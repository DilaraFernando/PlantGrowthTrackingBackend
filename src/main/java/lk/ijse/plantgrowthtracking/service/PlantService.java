package lk.ijse.plantgrowthtracking.service;

import lk.ijse.plantgrowthtracking.dto.*;

import java.util.List;

public interface PlantService {
    PlantResponse getPlantById(Long plantId, String userEmail);

    PlantResponse registerPlant(PlantRegisterRequest dto, String userEmail);
    List<PlantResponse> getMyPlants(String userEmail);
    List<GrowthLogResponse> getPlantLogs(Long plantId, String userEmail);
    List<NotificationResponse> getMyNotifications(String userEmail);
    void markNotificationRead(Long notificationId, String userEmail);
}
