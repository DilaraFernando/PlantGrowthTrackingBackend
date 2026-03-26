package lk.ijse.plantgrowthtracking.service;

import lk.ijse.plantgrowthtracking.entity.User;

public interface UserService {
    User findUserByEmail(String email);

    User findUserByUsername(String username);
    
    // Settings API
    lk.ijse.plantgrowthtracking.dto.UserSettingsResponse getSettings(String email);
    lk.ijse.plantgrowthtracking.dto.UserSettingsResponse updateSettings(String email, lk.ijse.plantgrowthtracking.dto.UserSettingsRequest request);
}