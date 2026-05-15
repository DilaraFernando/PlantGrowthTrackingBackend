package lk.ijse.plantgrowthtracking.controller;

import lk.ijse.plantgrowthtracking.dto.JourneyRequestDTO;
import lk.ijse.plantgrowthtracking.service.PlantJourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/journey")
@CrossOrigin(origins = "*")
public class PlantJourneyController {

    @Autowired
    private PlantJourneyService service;

    @PostMapping("/share")
    public ResponseEntity<String> sharePost(@RequestBody JourneyRequestDTO dto) {
        try {
            service.savePublicJourney(dto);
            return ResponseEntity.ok("Shared to Public Journey successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
