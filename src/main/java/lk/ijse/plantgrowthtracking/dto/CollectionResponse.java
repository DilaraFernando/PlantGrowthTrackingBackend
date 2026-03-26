package lk.ijse.plantgrowthtracking.dto;

import java.time.LocalDate;

import lk.ijse.plantgrowthtracking.entity.PlantCollection;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CollectionResponse {
    private Long id;
    private String name;
    private String category;
    private String location;
    private LocalDate createdAt;

    public static CollectionResponse fromEntity(PlantCollection collection) {
        return new CollectionResponse(
                collection.getId(),
                collection.getName(),
                collection.getCategory().name(),
                collection.getLocation(),
                collection.getCreatedAt()
        );
    }
}
