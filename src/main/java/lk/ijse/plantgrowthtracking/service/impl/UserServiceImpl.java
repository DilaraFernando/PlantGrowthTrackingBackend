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
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public lk.ijse.plantgrowthtracking.dto.UserSettingsResponse getSettings(String email) {
        User user = findUserByEmail(email);
        return new lk.ijse.plantgrowthtracking.dto.UserSettingsResponse(
                user.getTimezone(), user.getLocale(), user.getNotifyByEmail(),
                user.getDisplayName(), user.getUnits(), user.getDarkMode(), user.getPushNotifications()
        );
    }

    @Override
    @Transactional
    public lk.ijse.plantgrowthtracking.dto.UserSettingsResponse updateSettings(String email, lk.ijse.plantgrowthtracking.dto.UserSettingsRequest request) {
        User user = findUserByEmail(email);
        if (request.getTimezone() != null) user.setTimezone(request.getTimezone());
        if (request.getLocale() != null) user.setLocale(request.getLocale());
        if (request.getNotifyByEmail() != null) user.setNotifyByEmail(request.getNotifyByEmail());
        if (request.getDisplayName() != null) user.setDisplayName(request.getDisplayName());
        if (request.getUnits() != null) user.setUnits(request.getUnits());
        if (request.getDarkMode() != null) user.setDarkMode(request.getDarkMode());
        if (request.getPushNotifications() != null) user.setPushNotifications(request.getPushNotifications());
        userRepository.save(user);
        return new lk.ijse.plantgrowthtracking.dto.UserSettingsResponse(
                user.getTimezone(), user.getLocale(), user.getNotifyByEmail(),
                user.getDisplayName(), user.getUnits(), user.getDarkMode(), user.getPushNotifications()
        );
    }
}