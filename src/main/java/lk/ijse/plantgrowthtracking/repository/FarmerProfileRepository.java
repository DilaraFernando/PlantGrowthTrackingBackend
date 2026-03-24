package lk.ijse.plantgrowthtracking.repository;

import lk.ijse.plantgrowthtracking.entity.FarmerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FarmerProfileRepository extends JpaRepository<FarmerProfile, Long> {

}