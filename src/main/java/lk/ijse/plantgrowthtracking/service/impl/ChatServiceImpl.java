package lk.ijse.plantgrowthtracking.service.impl;

import lk.ijse.plantgrowthtracking.dto.ChatResponse;
import lk.ijse.plantgrowthtracking.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${groq.api.key}")
    private String groqApiKey;

    @Value("${groq.api.url}")
    private String groqApiUrl;

    @Value("${groq.model}")
    private String groqModel;

    @Override
    public ChatResponse chat(String message, String userEmail) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + groqApiKey);

        List<Map<String, String>> messages = List.of(
                Map.of("role", "system", "content", "You are a helpful plant growth and agriculture expert assistant. Help users with plant care, disease detection, soil advice, and growth tips."),
                Map.of("role", "user", "content", message)
        );

        Map<String, Object> requestBody = Map.of(
                "model", groqModel,
                "messages", messages
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(groqApiUrl, entity, Map.class);

        Map<String, Object> body = response.getBody();
        List<Map<String, Object>> choices = (List<Map<String, Object>>) body.get("choices");
        Map<String, Object> messageObj = (Map<String, Object>) choices.get(0).get("message");
        String reply = (String) messageObj.get("content");

        return ChatResponse.builder()
                .reply(reply)
                .timestamp(Instant.now().toString())
                .build();
    }
}
