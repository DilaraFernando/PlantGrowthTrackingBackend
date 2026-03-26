package lk.ijse.plantgrowthtracking.repository;

import lk.ijse.plantgrowthtracking.entity.GrowthLog;
import lk.ijse.plantgrowthtracking.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GrowthLogRepository extends JpaRepository<GrowthLog, Long> {
    List<GrowthLog> findByPlantOrderByDayNumberAsc(Plant plant);
}
