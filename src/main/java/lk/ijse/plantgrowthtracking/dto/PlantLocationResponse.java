package lk.ijse.plantgrowthtracking.dto;

public class PlantLocationResponse {
    private Long id;
    private String plantName;
    private String section;
    private String rowPosition;
    private Integer gridIndex;

    public PlantLocationResponse(Long id, String plantName, String section, String rowPosition, Integer gridIndex) {
        this.id = id;
        this.plantName = plantName;
        this.section = section;
        this.rowPosition = rowPosition;
        this.gridIndex = gridIndex;
    }

    public Long getId() { return id; }
    public String getPlantName() { return plantName; }
    public String getSection() { return section; }
    public String getRowPosition() { return rowPosition; }
    public Integer getGridIndex() { return gridIndex; }
}