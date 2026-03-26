
package lk.ijse.plantgrowthtracking.dto;

import java.time.LocalDate;
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
public class GrowthLogResponse {
    private int dayNumber;
    private String stage;
    private String notes;
    private LocalDate logDate;

    // ...existing code...
}
