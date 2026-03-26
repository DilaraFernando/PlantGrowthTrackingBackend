package lk.ijse.plantgrowthtracking;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PlantGrowthTrackingApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlantGrowthTrackingApplication.class, args);
    }

}
