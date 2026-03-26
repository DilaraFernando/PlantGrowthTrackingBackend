package lk.ijse.plantgrowthtracking.controller;

import lk.ijse.plantgrowthtracking.dto.*;
import lk.ijse.plantgrowthtracking.service.PlantService;
import lk.ijse.plantgrowthtracking.service.SocialPostService;
import lk.ijse.plantgrowthtracking.scheduler.GrowthScheduler;
import lk.ijse.plantgrowthtracking.util.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plants")
public class PlantController {
    @Autowired
    private PlantService plantService;

    @Autowired
    private SocialPostService socialPostService;

    @Autowired
    private GrowthScheduler growthScheduler;

    @PostMapping("/register")
    public ResponseEntity<APIResponse<PlantResponse>> registerPlant(@RequestBody PlantRegisterRequest dto, Authentication authentication) {
        String email = authentication.getName();
        PlantResponse response = plantService.registerPlant(dto, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(APIResponse.success(response));
    }

    @GetMapping("")
    public ResponseEntity<APIResponse<List<PlantResponse>>> getMyPlants(Authentication authentication) {
        String email = authentication.getName();
        List<PlantResponse> list = plantService.getMyPlants(email);
        return ResponseEntity.ok(APIResponse.success(list));
    }

    @GetMapping("/{plantId}/logs")
    public ResponseEntity<APIResponse<List<GrowthLogResponse>>> getPlantLogs(@PathVariable Long plantId, Authentication authentication) {
        String email = authentication.getName();
        List<GrowthLogResponse> logs = plantService.getPlantLogs(plantId, email);
        return ResponseEntity.ok(APIResponse.success(logs));
    }

    @GetMapping("/notifications")
    public ResponseEntity<APIResponse<List<NotificationResponse>>> getMyNotifications(Authentication authentication) {
        String email = authentication.getName();
        List<NotificationResponse> notifications = plantService.getMyNotifications(email);
        return ResponseEntity.ok(APIResponse.success(notifications));
    }

    @PatchMapping("/notifications/{id}/read")
    public ResponseEntity<APIResponse<Void>> markNotificationRead(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        plantService.markNotificationRead(id, email);
        return ResponseEntity.ok(APIResponse.success(null));
    }

    @GetMapping("/{plantId}/social-posts")
    public ResponseEntity<APIResponse<List<SocialPostResponse>>> getPlantSocialPosts(@PathVariable Long plantId, Authentication authentication) {
        String email = authentication.getName();
        List<SocialPostResponse> posts = socialPostService.getPostsForPlant(plantId, email);
        return ResponseEntity.ok(APIResponse.success(posts));
    }

    @PostMapping("/{plantId}/social-posts/generate")
    public ResponseEntity<APIResponse<SocialPostResponse>> generatePost(
            @PathVariable Long plantId,
            Authentication authentication) {
        String email = authentication.getName();
        SocialPostResponse post = socialPostService.generatePostForPlant(plantId, email);
        return ResponseEntity.ok(APIResponse.success(post));
    }

    @PostMapping("/admin/trigger-daily-update")
    public ResponseEntity<APIResponse<String>> triggerDailyUpdate() {
        growthScheduler.runDailyGrowthUpdate();
        return ResponseEntity.ok(APIResponse.success("Daily update triggered"));
    }
}