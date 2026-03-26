package lk.ijse.plantgrowthtracking.controller;

import lk.ijse.plantgrowthtracking.dto.WeatherResponse;
import lk.ijse.plantgrowthtracking.service.WeatherService;
import lk.ijse.plantgrowthtracking.util.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/weather")
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/current")
    public APIResponse<WeatherResponse> getCurrent(@RequestParam String city) {
        WeatherResponse res = weatherService.getCurrentWeather(city);
        return new APIResponse<>(200, "Success", res);
    }

    @GetMapping("/alert")
    public ResponseEntity<APIResponse<String>> getPlantAlert(
            @RequestParam String plantType,
            @RequestParam String city) {
        WeatherResponse weather = weatherService.getCurrentWeather(city);
        String alert;
        if (weather.getTemperature() > 35) {
            alert = "Too hot for " + plantType + ". Consider watering more frequently.";
        } else if (weather.getTemperature() < 10) {
            alert = "Too cold for " + plantType + ". Move to a warmer location.";
        } else if (weather.getHumidity() < 30) {
            alert = "Low humidity detected. Increase watering for your " + plantType + ".";
        } else if (weather.getWindSpeed() > 20) {
            alert = "High winds detected. Protect your " + plantType + " from damage.";
        } else {
            alert = "Conditions are suitable for your " + plantType + ". Keep up the good work!";
        }
        return ResponseEntity.ok(new APIResponse<>(200, "Success", alert));
    }
}