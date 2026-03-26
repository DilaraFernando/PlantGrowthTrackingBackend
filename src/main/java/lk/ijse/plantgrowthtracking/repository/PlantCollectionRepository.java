package lk.ijse.plantgrowthtracking.repository;

import lk.ijse.plantgrowthtracking.entity.PlantCollection;
import lk.ijse.plantgrowthtracking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlantCollectionRepository extends JpaRepository<PlantCollection, Long> {
    List<PlantCollection> findByOwner(User owner);
    boolean existsByNameAndOwner(String name, User owner);
}
