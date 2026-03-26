package lk.ijse.plantgrowthtracking.service;

import lk.ijse.plantgrowthtracking.dto.AuthRequest;
import lk.ijse.plantgrowthtracking.dto.AuthResponse;
import lk.ijse.plantgrowthtracking.dto.RegisterRequest;

import java.util.Map;

public interface AuthService {

    AuthResponse register(RegisterRequest request);
    AuthResponse authenticate(AuthRequest request);
    void completeProfile(Map<String, String> details);

}