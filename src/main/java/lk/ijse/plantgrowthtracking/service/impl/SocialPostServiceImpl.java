package lk.ijse.plantgrowthtracking.service.impl;

import lk.ijse.plantgrowthtracking.entity.Plant;
import lk.ijse.plantgrowthtracking.entity.SocialPost;
import lk.ijse.plantgrowthtracking.repository.SocialPostRepository;
import lk.ijse.plantgrowthtracking.service.SocialPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class SocialPostServiceImpl implements SocialPostService {
    @Autowired
    private SocialPostRepository socialPostRepository;

    @Override
    public SocialPost generatePostForPlant(Plant plant) {
        String caption = "Day " + plant.getCurrentDay() + " of my " + plant.getPlantName() + " growth journey! Currently in the " + plant.getCurrentStage() + " stage.\n#PlantGrowth #GrowthTracking #Day" + plant.getCurrentDay() + " #" + plant.getPlantType();
        SocialPost post = new SocialPost();
        post.setPlant(plant);
        post.setCaption(caption);
        post.setStatus("PENDING");
        post.setCreatedAt(LocalDateTime.now());
        return socialPostRepository.save(post);
    }
}
