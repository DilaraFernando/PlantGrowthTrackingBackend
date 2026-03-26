package lk.ijse.plantgrowthtracking.service;

import lk.ijse.plantgrowthtracking.dto.WeatherResponse;

public interface WeatherService {
    WeatherResponse getCurrentWeather(String city);
}
