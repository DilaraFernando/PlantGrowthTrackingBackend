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
import java.util.stream.Collectors;

@Service
@Transactional
public class PlantServiceImpl implements PlantService {
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
            // Simple alert logic based on status and stage
            if (plant.getStatus() != null && plant.getStatus().toUpperCase().contains("THIRSTY")) {
                return "Danger: Your plant needs water!";
            }
            if (plant.getCurrentStage() != null && plant.getCurrentStage().toLowerCase().contains("harvest")) {
                return "Good: Your plant is ready for harvest.";
            }
            if (plant.getStatus() != null && plant.getStatus().toUpperCase().contains("HEALTHY")) {
                return "Safe: Your plant is healthy.";
            }
            return "No alert for this plant.";
        }
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

    private String getStageForDay(int day) {
        if (day >= 1 && day <= 3) return "Germination";
        if (day >= 4 && day <= 7) return "Seedling";
        if (day >= 8 && day <= 14) return "Early Growth";
        if (day >= 15 && day <= 21) return "Vegetative";
        if (day >= 22 && day <= 27) return "Maturation";
        return "Harvest Ready";
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
    public PlantResponse registerPlant(PlantRegisterRequest dto, String userEmail) {
        log.info("Registering plant for user: {}", userEmail);
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Plant plant = new Plant();
        plant.setPlantName(dto.getPlantName());
        plant.setPlantType(dto.getPlantType());
        plant.setPlantedDate(LocalDate.now());
        plant.setCurrentDay(1);
        plant.setCurrentStage(getStageForDay(1));
        plant.setStatus("HEALTHY");
        plant.setOwner(user);
        Plant saved = plantRepository.save(plant);
        return toPlantResponse(saved);
    }

    @Override
    public List<PlantResponse> getMyPlants(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Plant> plants = plantRepository.findByOwnerAndStatus(user, "HEALTHY");
        return plants.stream().map(this::toPlantResponse).collect(Collectors.toList());
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

    // Helper methods
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
