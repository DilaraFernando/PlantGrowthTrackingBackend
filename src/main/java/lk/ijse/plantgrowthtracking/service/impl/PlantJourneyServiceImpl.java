package lk.ijse.plantgrowthtracking.service.impl;

import lk.ijse.plantgrowthtracking.dto.JourneyRequestDTO;
import lk.ijse.plantgrowthtracking.entity.PlantJourney;
import lk.ijse.plantgrowthtracking.repository.PlantJourneyRepository;
import lk.ijse.plantgrowthtracking.service.PlantJourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class PlantJourneyServiceImpl implements PlantJourneyService {

    @Autowired
    private PlantJourneyRepository repository;

    @Override
    public void savePublicJourney(JourneyRequestDTO dto) {
        PlantJourney journey = new PlantJourney();

        journey.setDayTag(dto.getDayTag());
        journey.setHealth(dto.getHealth());
        journey.setHeight(dto.getHeight());
        journey.setLeaves(dto.getLeaves());

        // This maps the giant string from your JS to the LONGTEXT column
        journey.setImageBase64(dto.getImageBase64());

        journey.setSharedAt(LocalDateTime.now());

        repository.save(journey);
    }
}