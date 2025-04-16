package lk.ijse.final_project_aad.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ActivityDTO {
    private String type;
    private String message;
    private LocalDateTime timestamp;
    private String performedBy;

    public ActivityDTO(String type, String message, LocalDateTime timestamp, String performedBy) {
        this.type = type;
        this.message = message;
        this.timestamp = timestamp;
        this.performedBy = performedBy;
    }
}