package lk.ijse.plantgrowthtracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSettingsRequest {
    private String timezone;
    private String locale;
    private Boolean notifyByEmail;
    private String displayName;
    private String units;
    private Boolean darkMode;
    private Boolean pushNotifications;
}
