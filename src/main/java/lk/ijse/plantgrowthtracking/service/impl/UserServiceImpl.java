package lk.ijse.plantgrowthtracking.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.plantgrowthtracking.dto.AuthResponse;
import lk.ijse.plantgrowthtracking.dto.RegisterRequest;
import lk.ijse.plantgrowthtracking.entity.User;
import lk.ijse.plantgrowthtracking.repository.UserRepository;
import lk.ijse.plantgrowthtracking.service.UserService;
import lk.ijse.plantgrowthtracking.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already register");
        }
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword() !- null ? password)

    }
}