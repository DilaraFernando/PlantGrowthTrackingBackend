package lk.ijse.plantgrowthtracking.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse<T> {
    private int status;
    private String message;
    private T data;

    public static <T> APIResponse<T> success(T data) {
        return new APIResponse<>(200, "Success", data);
    }

    public static <T> APIResponse<T> error(int status, String message) {
        return new APIResponse<>(status, message, null);
    }
}
