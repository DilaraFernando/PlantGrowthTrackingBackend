package lk.ijse.plantgrowthtracking.service.impl;

import lk.ijse.plantgrowthtracking.dto.SocialPostResponse;
import lk.ijse.plantgrowthtracking.entity.Plant;
import lk.ijse.plantgrowthtracking.entity.SocialPost;
import lk.ijse.plantgrowthtracking.repository.SocialPostRepository;
import lk.ijse.plantgrowthtracking.repository.PlantRepository;
import lk.ijse.plantgrowthtracking.repository.UserRepository;
import lk.ijse.plantgrowthtracking.service.SocialPostService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SocialPostServiceImpl implements SocialPostService {
    private static final Logger log = LoggerFactory.getLogger(SocialPostServiceImpl.class);
    private final SocialPostRepository socialPostRepository;
    private final PlantRepository plantRepository;
    private final UserRepository userRepository;

    @Override
    public SocialPostResponse generatePostForPlant(Long plantId, String userEmail) {
        log.info("Generating post for plant id: {}", plantId);
        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(() -> new RuntimeException("Plant not found"));
        if (!plant.getOwner().getEmail().equals(userEmail)) {
            throw new RuntimeException("Unauthorized: Plant does not belong to user");
        }
        SocialPost savedPost = generatePostForPlant(plant);
        log.info("Saved post id: {}", savedPost.getId());
        return new SocialPostResponse(savedPost.getId(), savedPost.getCaption(), savedPost.getStatus(), savedPost.getCreatedAt(), savedPost.getDayNumber());
    }

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
        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(() -> new RuntimeException("Plant not found"));
        if (!plant.getOwner().getEmail().equals(userEmail)) {
            throw new RuntimeException("Unauthorized: Plant does not belong to user");
        }
        List<SocialPost> posts = socialPostRepository.findByPlant(plant);
        return posts.stream()
                .map(post -> new SocialPostResponse(post.getId(), post.getCaption(), post.getStatus(), post.getCreatedAt(), post.getDayNumber()))
                .toList();
    }
}