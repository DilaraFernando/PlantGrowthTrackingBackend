package lk.ijse.plantgrowthtracking.service;

import lk.ijse.plantgrowthtracking.dto.*;

import java.util.List;

public interface PlantService {
        String getPlantAlert(Long plantId, String userEmail);
    PlantResponse getPlantById(Long plantId, String userEmail);

    PlantResponse registerPlant(PlantRegisterRequest dto, String userEmail);
    List<PlantResponse> getMyPlants(String userEmail);
    List<GrowthLogResponse> getPlantLogs(Long plantId, String userEmail);
    List<NotificationResponse> getMyNotifications(String userEmail);
    void markNotificationRead(Long notificationId, String userEmail);

    List<SectionSummaryResponse> getSectionSummaries(String userEmail);
    List<PlantLocationResponse> getPlantsInSection(String userEmail, String section);
    List<PlantLocationResponse> getAllPlantLocations(String userEmail);
}
