package lk.ijse.plantgrowthtracking.repository;

import lk.ijse.plantgrowthtracking.entity.PlantJourney;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantJourneyRepository extends JpaRepository<PlantJourney, Long> {
}
