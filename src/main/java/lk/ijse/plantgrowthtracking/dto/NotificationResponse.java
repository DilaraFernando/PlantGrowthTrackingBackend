
package lk.ijse.plantgrowthtracking.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private Long id;
    private String message;
    private String type;
    private boolean read;
    private LocalDateTime createdAt;

    // ...existing code...
}
