package lk.ijse.plantgrowthtracking.service.impl;

import lk.ijse.plantgrowthtracking.dto.SocialPostResponse;
import lk.ijse.plantgrowthtracking.entity.Plant;
import lk.ijse.plantgrowthtracking.entity.SocialPost;
import lk.ijse.plantgrowthtracking.repository.SocialPostRepository;
import lk.ijse.plantgrowthtracking.service.SocialPostService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SocialPostServiceImpl implements SocialPostService {
    private final SocialPostRepository socialPostRepository;

    @Override
    public SocialPost generatePostForPlant(Plant plant) {
        String caption = "Day " + plant.getCurrentDay() + 
            " of my " + plant.getPlantName() + 
            " growth journey! Currently in the " + 
            plant.getCurrentStage() + " stage. " +
            "#PlantGrowth #GrowthTracking #Day" + 
            plant.getCurrentDay() + " #" + plant.getPlantType();

        SocialPost post = new SocialPost();
        post.setPlant(plant);
        post.setCaption(caption);
        post.setStatus("PENDING");
        post.setCreatedAt(LocalDateTime.now());

        return socialPostRepository.save(post);
    }

    @Override
    public List<SocialPostResponse> getPostsForPlant(Long plantId, String userEmail) {
        return List.of();
    }
}
