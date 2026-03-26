package lk.ijse.plantgrowthtracking.controller;

import lk.ijse.plantgrowthtracking.dto.WeatherResponse;
import lk.ijse.plantgrowthtracking.service.WeatherService;
import lk.ijse.plantgrowthtracking.util.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/current")
    public ResponseEntity<APIResponse<WeatherResponse>> getCurrent(@RequestParam(value = "city", required = false, defaultValue = "Colombo") String city) {
        WeatherResponse resp = weatherService.getCurrentWeather(city);
        return ResponseEntity.ok(APIResponse.success(resp));
    }

}