package lk.ijse.plantgrowthtracking.service.impl;

import lk.ijse.plantgrowthtracking.dto.*;
import lk.ijse.plantgrowthtracking.entity.*;
import lk.ijse.plantgrowthtracking.repository.*;
import lk.ijse.plantgrowthtracking.service.PlantService;
import lk.ijse.plantgrowthtracking.service.SocialPostService;
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
    public PlantResponse registerPlant(PlantRegisterRequest dto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Plant plant = new Plant();
        plant.setPlantName(dto.getPlantName());
        plant.setPlantType(dto.getPlantType());
        plant.setPlantedDate(LocalDate.now());
        plant.setCurrentDay(1);
        plant.setCurrentStage(getStageForDay(1));
        plant.setStatus("ACTIVE");
        plant.setOwner(user);
        plant = plantRepository.save(plant);
        GrowthLog log = new GrowthLog();
        log.setPlant(plant);
        log.setDayNumber(1);
        log.setStage(getStageForDay(1));
        log.setNotes("");
        log.setLogDate(LocalDate.now());
        growthLogRepository.save(log);
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage("Your plant [" + plant.getPlantName() + "] has been successfully registered. Day 1 of 30 begins today!");
        notification.setType("PLANTING_SUCCESS");
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        notificationRepository.save(notification);
        return toPlantResponse(plant);
    }

    @Override
    public List<PlantResponse> getMyPlants(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return plantRepository.findByOwnerAndStatus(user, "ACTIVE").stream()
                .map(this::toPlantResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<GrowthLogResponse> getPlantLogs(Long plantId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(() -> new RuntimeException("Plant not found"));
        if (!plant.getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access to plant");
        }
        return growthLogRepository.findByPlantOrderByDayNumberAsc(plant).stream()
                .map(this::toGrowthLogResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponse> getMyNotifications(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return notificationRepository.findByUserOrderByCreatedAtDesc(user).stream()
                .map(this::toNotificationResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void markNotificationRead(Long notificationId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        if (!notification.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access to notification");
        }
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    private PlantResponse toPlantResponse(Plant plant) {
        PlantResponse res = new PlantResponse();
        res.setId(plant.getId());
        res.setPlantName(plant.getPlantName());
        res.setPlantType(plant.getPlantType());
        res.setPlantedDate(plant.getPlantedDate());
        res.setCurrentDay(plant.getCurrentDay());
        res.setCurrentStage(plant.getCurrentStage());
        res.setStatus(plant.getStatus());
        return res;
    }

    private GrowthLogResponse toGrowthLogResponse(GrowthLog log) {
        GrowthLogResponse res = new GrowthLogResponse();
        res.setDayNumber(log.getDayNumber());
        res.setStage(log.getStage());
        res.setNotes(log.getNotes());
        res.setLogDate(log.getLogDate());
        return res;
    }

    private NotificationResponse toNotificationResponse(Notification n) {
        NotificationResponse res = new NotificationResponse();
        res.setId(n.getId());
        res.setMessage(n.getMessage());
        res.setType(n.getType());
        res.setRead(n.isRead());
        res.setCreatedAt(n.getCreatedAt());
        return res;
    }
}