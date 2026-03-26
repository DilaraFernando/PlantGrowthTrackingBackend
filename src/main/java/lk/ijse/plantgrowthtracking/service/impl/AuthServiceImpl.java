package lk.ijse.plantgrowthtracking.service.impl;

import lk.ijse.plantgrowthtracking.dto.AuthRequest;
import lk.ijse.plantgrowthtracking.dto.AuthResponse;
import lk.ijse.plantgrowthtracking.dto.RegisterRequest;
import lk.ijse.plantgrowthtracking.entity.User;
import lk.ijse.plantgrowthtracking.exception.AlreadyExistsException;
import lk.ijse.plantgrowthtracking.exception.BadCredentialsException;
import lk.ijse.plantgrowthtracking.exception.EmailNotFoundException;
import lk.ijse.plantgrowthtracking.repository.UserRepository;
import lk.ijse.plantgrowthtracking.service.AuthService;
import lk.ijse.plantgrowthtracking.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException("Email already exists");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUsername(request.getEmail().split("@")[0]);
        user.setRole("USER");
        userRepository.save(user);
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EmailNotFoundException("Email not found"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        String token = jwtUtil.generateToken(user.getEmail());
        return AuthResponse.builder().accessToken(token).build();
    }

    @Override
    public void completeProfile(java.util.Map<String, String> details) {
        // Not implemented
    }
}