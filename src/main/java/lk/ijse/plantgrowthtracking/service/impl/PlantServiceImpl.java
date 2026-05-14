package lk.ijse.plantgrowthtracking.service.impl;

import lk.ijse.plantgrowthtracking.dto.*;
import lk.ijse.plantgrowthtracking.entity.*;
import lk.ijse.plantgrowthtracking.repository.*;
import lk.ijse.plantgrowthtracking.service.PlantService;
import lk.ijse.plantgrowthtracking.service.SocialPostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlantServiceImpl implements PlantService {

    private static final Logger log = LoggerFactory.getLogger(PlantServiceImpl.class);

    @Autowired
    private PlantRepository plantRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GrowthLogRepository growthLogRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private SocialPostService socialPostService;

    @Override
    public PlantResponse registerPlant(PlantRegisterRequest dto, String userEmail) {
        log.info("Registering plant for user: {}", userEmail);
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 1. plant save
        Plant plant = new Plant();
        plant.setPlantName(dto.getPlantName());
        plant.setPlantType(dto.getPlantType());
        plant.setPlantedDate(LocalDate.now());
        plant.setCurrentDay(1);
        plant.setCurrentStage(getStageForDay(1));
        plant.setStatus("PENDING"); // first pending
        plant.setOwner(user);

        // Grid/Location detail include
        plant.setSection(dto.getSection());
        plant.setRowPosition(dto.getRowPosition());
        plant.setGridIndex(dto.getGridIndex());

        Plant savedPlant = plantRepository.save(plant);

        // 2. first Notification one: Waiting for locator
        createInternalNotification(user, "Waiting for your plant locator: " + savedPlant.getPlantName(), "WAITING");

        // 3. one minute Location Success notification (Asynchronous Task)
        CompletableFuture.runAsync(() -> {
            try {
                // one minute late
                TimeUnit.MINUTES.sleep(1);

                // plant status  HEALTHY  Success notification
                savedPlant.setStatus("HEALTHY");
                plantRepository.save(savedPlant);

                createInternalNotification(user, "Success: Your Plant locator pinned for " + savedPlant.getPlantName(), "LOCATED");

                log.info("Location successfully pinned for plant: {}", savedPlant.getPlantName());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Location simulation interrupted", e);
            }
        });

        return toPlantResponse(savedPlant);
    }

    // Notification create Helper method
    private void createInternalNotification(User user, String message, String type) {
        Notification notification = Notification.builder()
                .user(user)
                .message(message)
                .type(type)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();
        notificationRepository.save(notification);
    }

    @Override
    public List<NotificationResponse> getMyNotifications(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return notificationRepository.findByUserOrderByCreatedAtDesc(user)
                .stream().map(this::toNotificationResponse).collect(Collectors.toList());
    }

    @Override
    public void markNotificationRead(Long notificationId, String userEmail) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        if (!notification.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Unauthorized access to notification");
        }
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    @Override
    public List<PlantResponse> getMyPlants(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        //  Status  HEALTHY plant
        List<Plant> plants = plantRepository.findByOwnerAndStatus(user, "HEALTHY");
        return plants.stream().map(this::toPlantResponse).collect(Collectors.toList());
    }

    @Override
    public PlantResponse getPlantById(Long plantId, String userEmail) {
        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(() -> new RuntimeException("Plant not found"));
        if (!plant.getOwner().getEmail().equals(userEmail)) {
            throw new RuntimeException("Unauthorized access to plant");
        }
        return toPlantResponse(plant);
    }

    @Override
    public List<SectionSummaryResponse> getSectionSummaries(String userEmail) {
        User owner = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
        List<Plant> plants = plantRepository.findByOwner(owner);
        return plants.stream()
                .filter(p -> p.getSection() != null)
                .collect(Collectors.groupingBy(Plant::getSection, Collectors.counting()))
                .entrySet().stream()
                .map(e -> new SectionSummaryResponse(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<PlantLocationResponse> getPlantsInSection(String userEmail, String section) {
        User owner = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
        List<Plant> plants = plantRepository.findByOwnerAndSection(owner, section);
        return plants.stream()
                .map(p -> new PlantLocationResponse(p.getId(), p.getPlantName(), p.getSection(), p.getRowPosition(), p.getGridIndex()))
                .collect(Collectors.toList());
    }

    @Override
    public List<PlantLocationResponse> getAllPlantLocations(String userEmail) {
        User owner = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
        List<Plant> plants = plantRepository.findByOwner(owner);
        return plants.stream()
                .map(p -> new PlantLocationResponse(p.getId(), p.getPlantName(), p.getSection(), p.getRowPosition(), p.getGridIndex()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPlantAlert(Long plantId, String userEmail) {
        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(() -> new RuntimeException("Plant not found"));
        if (!plant.getOwner().getEmail().equals(userEmail)) {
            throw new RuntimeException("Unauthorized access to plant");
        }
        if (plant.getStatus() != null && plant.getStatus().toUpperCase().contains("THIRSTY")) {
            return "Danger: Your plant needs water!";
        }
        if (plant.getCurrentStage() != null && plant.getCurrentStage().toLowerCase().contains("harvest")) {
            return "Good: Your plant is ready for harvest.";
        }
        return "Safe: Your plant is healthy.";
    }

    @Override
    public List<GrowthLogResponse> getPlantLogs(Long plantId, String userEmail) {
        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(() -> new RuntimeException("Plant not found"));
        if (!plant.getOwner().getEmail().equals(userEmail)) {
            throw new RuntimeException("Unauthorized access to plant logs");
        }
        return growthLogRepository.findByPlantOrderByDayNumberAsc(plant)
                .stream().map(this::toGrowthLogResponse).collect(Collectors.toList());
    }

    private String getStageForDay(int day) {
        if (day >= 1 && day <= 3) return "Germination";
        if (day >= 4 && day <= 7) return "Seedling";
        if (day >= 8 && day <= 14) return "Early Growth";
        if (day >= 15 && day <= 21) return "Vegetative";
        if (day >= 22 && day <= 27) return "Maturation";
        return "Harvest Ready";
    }

    // Mapping Methods
    private PlantResponse toPlantResponse(Plant plant) {
        return PlantResponse.builder()
                .id(plant.getId())
                .plantName(plant.getPlantName())
                .plantType(plant.getPlantType())
                .plantedDate(plant.getPlantedDate())
                .currentDay(plant.getCurrentDay())
                .currentStage(plant.getCurrentStage())
                .status(plant.getStatus())
                .build();
    }

    private GrowthLogResponse toGrowthLogResponse(GrowthLog log) {
        return GrowthLogResponse.builder()
                .dayNumber(log.getDayNumber())
                .stage(log.getStage())
                .notes(log.getNotes())
                .logDate(log.getLogDate())
                .build();
    }

    private NotificationResponse toNotificationResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .type(notification.getType())
                .isRead(notification.getIsRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}