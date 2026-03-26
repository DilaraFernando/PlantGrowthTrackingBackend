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
    public ResponseEntity<APIResponse<WeatherResponse>> getCurrent(@RequestParam String city) {
        WeatherResponse res = weatherService.getCurrentWeather(city);
        return ResponseEntity.ok(new APIResponse<>(200, "Success", res));
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

    @GetMapping("/maintenance")
    public ResponseEntity<APIResponse<String>> getMaintenanceTip(
            @RequestParam String plantType,
            @RequestParam int currentDay) {

        String water;
        String fertilizer;

        if (currentDay <= 3) {
            water = "50ml per day";
            fertilizer = "None — germination stage";
        } else if (currentDay <= 7) {
            water = "100ml per day";
            fertilizer = "Diluted liquid fertilizer every 3 days";
        } else if (currentDay <= 14) {
            water = "150ml per day";
            fertilizer = "Nitrogen-rich fertilizer twice a week";
        } else if (currentDay <= 21) {
            water = "200ml per day";
            fertilizer = "Balanced NPK fertilizer every 5 days";
        } else if (currentDay <= 27) {
            water = "250ml per day";
            fertilizer = "Phosphorus-rich fertilizer every 4 days";
        } else {
            water = "300ml per day";
            fertilizer = "Potassium-rich fertilizer every 3 days";
        }

        String tip = "Day " + currentDay + " care for " + plantType
                + ": Water " + water
                + ". Fertilizer: " + fertilizer + ".";

        return ResponseEntity.ok(
                new APIResponse<>(200, "Success", tip));
    }
}