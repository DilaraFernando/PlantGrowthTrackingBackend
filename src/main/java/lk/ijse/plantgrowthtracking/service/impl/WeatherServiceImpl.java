package lk.ijse.plantgrowthtracking.service.impl;

import lk.ijse.plantgrowthtracking.dto.WeatherResponse;
import lk.ijse.plantgrowthtracking.service.WeatherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;

@Service
public class WeatherServiceImpl implements WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;
    // Hardcode the correct OpenWeatherMap endpoint for free tier
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public WeatherResponse getCurrentWeather(String city) {
        String url = API_URL + "?q=" + city + "&appid=" + apiKey + "&units=metric";
        Map response = restTemplate.getForObject(url, Map.class);
        Map main = (Map) response.get("main");
        Map wind = (Map) response.get("wind");
        Map weather0 = ((java.util.List<Map>) response.get("weather")).get(0);
        return new WeatherResponse(
            city,
            main.get("temp") != null ? Double.parseDouble(main.get("temp").toString()) : 0.0,
            main.get("humidity") != null ? Double.parseDouble(main.get("humidity").toString()) : 0.0,
            wind.get("speed") != null ? Double.parseDouble(wind.get("speed").toString()) : 0.0,
            weather0.get("description") != null ? weather0.get("description").toString() : "",
            weather0.get("icon") != null ? weather0.get("icon").toString() : ""
        );
    }
}
