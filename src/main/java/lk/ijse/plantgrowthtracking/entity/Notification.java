
package lk.ijse.plantgrowthtracking.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String message;
    private String type;
    @Column(name = "is_read")
    private boolean isRead = false;

    public boolean getIsRead() {
        return isRead;
    }
    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
    private LocalDateTime createdAt;

    // ...existing code...
}
