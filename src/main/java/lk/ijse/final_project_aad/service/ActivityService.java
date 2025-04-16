package lk.ijse.final_project_aad.service;

import lk.ijse.final_project_aad.dto.ActivityDTO;
import lk.ijse.final_project_aad.entity.User;
import java.util.List;

public interface ActivityService {
    /**
     * Retrieves recent activities (last 5 by default)
     * @return List of ActivityDTOs ordered by timestamp descending
     */
    List<ActivityDTO> getRecentActivity();

    /**
     * Retrieves recent activities with custom limit
     * @param limit Number of activities to return
     * @return List of ActivityDTOs
     */
    List<ActivityDTO> getRecentActivity(int limit);

    /**
     * Logs a new activity
     * @param type Activity type (e.g., "user", "hotel")
     * @param message Descriptive message
     * @param user User who performed the activity
     */
    void logActivity(String type, String message, User user);

    /**
     * Logs a new activity without user context
     * @param type Activity type
     * @param message Descriptive message
     */
    void logSystemActivity(String type, String message);
}