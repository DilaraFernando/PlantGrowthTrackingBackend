package lk.ijse.plantgrowthtracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialPostResponse {
    private Long id;
    private String caption;
    private String status;
    private LocalDateTime createdAt;
    private int dayNumber;
}
