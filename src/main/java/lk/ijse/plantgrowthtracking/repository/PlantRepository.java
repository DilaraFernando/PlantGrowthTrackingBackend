package lk.ijse.plantgrowthtracking.repository;

import lk.ijse.plantgrowthtracking.entity.Plant;
import lk.ijse.plantgrowthtracking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    List<Plant> findByOwnerAndStatus(User owner, String status);
    List<Plant> findByStatus(String status);
}
