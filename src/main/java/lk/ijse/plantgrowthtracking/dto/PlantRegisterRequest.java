
package lk.ijse.plantgrowthtracking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@Builder
public class PlantRegisterRequest {
    @NotBlank
    private String plantName;
    @NotBlank
    private String plantType;

    // ...existing code...
}
