package lk.ijse.plantgrowthtracking.controller;

import lk.ijse.plantgrowthtracking.dto.UserSettingsRequest;
import lk.ijse.plantgrowthtracking.dto.UserSettingsResponse;
import lk.ijse.plantgrowthtracking.service.UserService;
import lk.ijse.plantgrowthtracking.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/user/settings")
@RequiredArgsConstructor
public class UserSettingsController {

    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<APIResponse<UserSettingsResponse>> getSettings(Principal principal) {
        String email = principal.getName();
        UserSettingsResponse res = userService.getSettings(email);
        return ResponseEntity.ok(APIResponse.success(res));
    }

    @PutMapping("")
    public ResponseEntity<APIResponse<UserSettingsResponse>> updateSettings(@RequestBody UserSettingsRequest request, Principal principal) {
        String email = principal.getName();
        UserSettingsResponse res = userService.updateSettings(email, request);
        return ResponseEntity.ok(APIResponse.success(res));
    }
}
