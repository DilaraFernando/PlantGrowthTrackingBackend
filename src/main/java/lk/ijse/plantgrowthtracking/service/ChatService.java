package lk.ijse.plantgrowthtracking.service;

import lk.ijse.plantgrowthtracking.dto.ChatResponse;

public interface ChatService {
    ChatResponse chat(String message, String userEmail);
}
