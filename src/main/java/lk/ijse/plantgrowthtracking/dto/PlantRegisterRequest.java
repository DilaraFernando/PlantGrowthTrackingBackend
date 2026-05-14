
package lk.ijse.plantgrowthtracking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlantRegisterRequest {
    @NotBlank
    private String plantName;
    @NotBlank
    private String plantType;
    private String section;
    private String rowPosition;
    private Integer gridIndex;

    // ...existing code...
}
