package lk.ijse.plantgrowthtracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse {
    private String city;
    private double temperature;
    private double humidity;
    private double windSpeed;
    private String condition;
    private String icon;
}
