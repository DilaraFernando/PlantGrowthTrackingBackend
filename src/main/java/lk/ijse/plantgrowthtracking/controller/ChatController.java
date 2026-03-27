package lk.ijse.plantgrowthtracking.controller;

import lk.ijse.plantgrowthtracking.dto.ChatRequest;
import lk.ijse.plantgrowthtracking.dto.ChatResponse;
import lk.ijse.plantgrowthtracking.service.ChatService;
import lk.ijse.plantgrowthtracking.util.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/ask")
    public ResponseEntity<APIResponse<ChatResponse>> ask(@Valid @RequestBody ChatRequest request, Authentication authentication) {
        String email = authentication.getName();
        ChatResponse response = chatService.chat(request.getMessage(), email);
        return ResponseEntity.ok(APIResponse.success(response));
    }
}
