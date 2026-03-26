package lk.ijse.plantgrowthtracking.dto;

public class SectionSummaryResponse {
    private String section;
    private long plantCount;

    public SectionSummaryResponse(String section, long plantCount) {
        this.section = section;
        this.plantCount = plantCount;
    }

    public String getSection() { return section; }
    public long getPlantCount() { return plantCount; }
}