
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
public class PlantResponse {
    private Long id;
    private String plantName;
    private String plantType;
    private LocalDate plantedDate;
    private int currentDay;
    private String currentStage;
    private String status;

    // ...existing code...
}
