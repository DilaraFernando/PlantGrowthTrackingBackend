2026-03-26: Fixed weather endpoint to use OpenWeatherMap 2.5/weather, confirmed WeatherController and SecurityConfig are correct, as per instructions. Now returns JSON for /api/v1/weather/current?city=Colombo
# Instructions History - Plant Growth Tracking Backend

## Task: Implement 30-Day Plant Growth Tracking backend

### Status: COMPLETED

### Actions Taken:
1.  **Fixed Compilation Errors:**
    *   Updated `APIResponse.java` to include static `success` and `error` methods.
    *   Added `@NoArgsConstructor` and `@AllArgsConstructor` with corresponding Lombok imports to all relevant entities and DTOs:
        *   `Plant.java`
        *   `GrowthLog.java`
        *   `Notification.java`
        *   `SocialPost.java`
        *   `PlantResponse.java`
        *   `GrowthLogResponse.java`
        *   `NotificationResponse.java`
2.  **Verified Logic:**
    *   Confirmed `PlantService` implementation follows the 30-day growth logic.
    *   Confirmed `GrowthScheduler` is correctly implemented with daily updates and completion logic.
    *   Confirmed `SocialPostService` correctly generates captions for daily updates.
    *   Confirmed `SecurityConfig` correctly handles authentication for plant-related endpoints.
3.  **Build Verification:**
    *   Successfully compiled the project using `./mvnw clean compile`.

### Notes:
*   The project now compiles successfully and follows the requested package structure and naming conventions.
*   All endpoints in `PlantController` require authentication and use the standard `APIResponse` wrapper.
*   The scheduler is enabled in the main application class.
