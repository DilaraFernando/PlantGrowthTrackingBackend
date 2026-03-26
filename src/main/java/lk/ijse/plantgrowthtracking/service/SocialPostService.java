package lk.ijse.plantgrowthtracking.service;

import lk.ijse.plantgrowthtracking.entity.Plant;
import lk.ijse.plantgrowthtracking.entity.SocialPost;
import lk.ijse.plantgrowthtracking.dto.SocialPostResponse;
import java.util.List;

public interface SocialPostService {
    SocialPost generatePostForPlant(Plant plant);
    SocialPostResponse generatePostForPlant(Long plantId, String userEmail);
    List<SocialPostResponse> getPostsForPlant(Long plantId, String userEmail);
}
