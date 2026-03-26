package lk.ijse.plantgrowthtracking.service;

import lk.ijse.plantgrowthtracking.entity.Plant;
import lk.ijse.plantgrowthtracking.entity.SocialPost;

public interface SocialPostService {
    SocialPost generatePostForPlant(Plant plant);
}
