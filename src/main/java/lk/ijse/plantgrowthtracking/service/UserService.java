package lk.ijse.plantgrowthtracking.service;

import lk.ijse.plantgrowthtracking.entity.User;

public interface UserService {
    User findUserByEmail(String email);

    User findUserByUsername(String username);
}