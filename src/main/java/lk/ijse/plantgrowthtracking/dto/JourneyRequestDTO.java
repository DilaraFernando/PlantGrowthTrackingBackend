package lk.ijse.plantgrowthtracking.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class JourneyRequestDTO {
    private String dayTag;
    private String height;
    private String health;
    private String leaves;
    private String imageBase64;


}
