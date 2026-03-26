package lk.ijse.plantgrowthtracking.scheduler;

import lk.ijse.plantgrowthtracking.entity.*;
import lk.ijse.plantgrowthtracking.repository.*;
import lk.ijse.plantgrowthtracking.service.SocialPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class GrowthScheduler {
    @Autowired
    private PlantRepository plantRepository;
    @Autowired
    private GrowthLogRepository growthLogRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private SocialPostService socialPostService;

    private String getStageForDay(int day) {
        if (day >= 1 && day <= 3) return "Germination";
        if (day >= 4 && day <= 7) return "Seedling";
        if (day >= 8 && day <= 14) return "Early Growth";
        if (day >= 15 && day <= 21) return "Vegetative";
        if (day >= 22 && day <= 27) return "Maturation";
        return "Harvest Ready";
    }

    @Scheduled(cron = "0 0 8 * * *")
    @Transactional
    public void runDailyGrowthUpdate() {
        List<Plant> activePlants = plantRepository.findByStatus("ACTIVE");
        for (Plant plant : activePlants) {
            int nextDay = plant.getCurrentDay() + 1;
            plant.setCurrentDay(nextDay);
            String stage = getStageForDay(nextDay);
            plant.setCurrentStage(stage);
            if (nextDay >= 30) {
                plant.setStatus("COMPLETED");
                Notification completed = new Notification();
                completed.setUser(plant.getOwner());
                completed.setMessage("Congratulations! Your plant [" + plant.getPlantName() + "] has completed its 30-day growth cycle!");
                completed.setType("COMPLETED");
                completed.setRead(false);
                completed.setCreatedAt(LocalDateTime.now());
                notificationRepository.save(completed);
            } else {
                Notification daily = new Notification();
                daily.setUser(plant.getOwner());
                daily.setMessage("Day " + nextDay + " update for [" + plant.getPlantName() + "]: currently in [" + stage + "] stage.");
                daily.setType("DAILY_UPDATE");
                daily.setRead(false);
                daily.setCreatedAt(LocalDateTime.now());
                notificationRepository.save(daily);
            }
            GrowthLog log = new GrowthLog();
            log.setPlant(plant);
            log.setDayNumber(nextDay);
            log.setStage(stage);
            log.setNotes("");
            log.setLogDate(LocalDate.now());
            growthLogRepository.save(log);
            socialPostService.generatePostForPlant(plant);
            plantRepository.save(plant);
        }
    }
}
