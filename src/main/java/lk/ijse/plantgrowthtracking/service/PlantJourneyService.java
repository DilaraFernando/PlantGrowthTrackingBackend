package lk.ijse.plantgrowthtracking.service;

import lk.ijse.plantgrowthtracking.dto.JourneyRequestDTO;

import java.io.IOException;

public interface PlantJourneyService {
    void savePublicJourney(JourneyRequestDTO dto) throws IOException;
}