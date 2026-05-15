package lk.ijse.plantgrowthtracking.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "plant_journey")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlantJourney {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dayTag;
    private String health;
    private String height;
    private String leaves;

    @Lob
    @Column(name = "image_base64", columnDefinition = "LONGTEXT")
    private String imageBase64;

    private LocalDateTime sharedAt;

    // Optional: Keep this if you want to use it later,
    // but for now we focus on imageBase64
    private String imagePath;
}