package lk.ijse.plantgrowthtracking.service.impl;

import lk.ijse.plantgrowthtracking.dto.*;
import lk.ijse.plantgrowthtracking.entity.Role;
import lk.ijse.plantgrowthtracking.entity.User;
import lk.ijse.plantgrowthtracking.repository.*;
import lk.ijse.plantgrowthtracking.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final ExpertProfileRepository expertProfileRepository;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return AuthResponse.builder()
                    .message("Email already in use!")
                    .build();
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .build();

        User savedUser = userRepository.save(user);

        if (request.getRole() == Role.EXPERT) {
            ExpertProfile expertProfile = ExpertProfile.builder()
                    .user(savedUser)
                    .specialization(request.getSpecialization())
                    .qualifications(request.getQualifications())
                    .build();
            expertProfileRepository.save(expertProfile);
        } else {
            UserProfileDTO userProfile = UserProfileDTO.builder()
                    .user(savedUser)
                    .phoneNumber(request.getPhoneNumber())
                    .plantType(request.getPlantType())
                    .dateOfBirth(request.getDateOfBirth())
                    .build();
            userProfileRepository.save(userProfile);
        }
        return AuthResponse.builder()
                .username(savedUser.getUsername())
                .role(savedUser.getRole().name())
                .message("Registration Successful!")
                .build();
    }
    @Override
    public AuthResponse authenticate(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user != null && user.getPassword().equals(request.getPassword())) {
            return AuthResponse.builder()
                    .username(user.getUsername())
                    .role(user.getRole().name())
                    .token("sample-jwt-token")
                    .message("Login Successful!")
                    .build();
        }

        return AuthResponse.builder()
                .message("Invalid Email or Password!")
                .build();
    }

    @Override
    public void completeProfile(Map<String, String> details) {
        System.out.println("Processing profile completion for: " + details.get("email"));
    }
}