package lk.ijse.plantgrowthtracking.dto;

import jakarta.validation.constraints.NotBlank;

import lk.ijse.plantgrowthtracking.entity.PlantCollection;

public class CollectionCreateRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String category;
    private String location;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public PlantCollection.Category getCategoryEnum() {
        return PlantCollection.Category.valueOf(category);
    }
}
