package lk.ijse.plantgrowthtracking.repository;

import lk.ijse.plantgrowthtracking.entity.ExpertProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertProfileRepository extends JpaRepository<ExpertProfile, Long> {
}