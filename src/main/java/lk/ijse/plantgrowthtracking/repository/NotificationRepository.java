package lk.ijse.plantgrowthtracking.repository;

import lk.ijse.plantgrowthtracking.entity.Notification;
import lk.ijse.plantgrowthtracking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserOrderByCreatedAtDesc(User user);
}
