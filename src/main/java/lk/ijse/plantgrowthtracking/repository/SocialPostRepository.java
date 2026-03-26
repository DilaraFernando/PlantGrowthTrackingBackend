package lk.ijse.plantgrowthtracking.repository;

import lk.ijse.plantgrowthtracking.entity.SocialPost;
import lk.ijse.plantgrowthtracking.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SocialPostRepository extends JpaRepository<SocialPost, Long> {
    List<SocialPost> findByPlant(Plant plant);
}
