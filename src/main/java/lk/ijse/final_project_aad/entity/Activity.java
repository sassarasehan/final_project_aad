package lk.ijse.final_project_aad.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String type;
    private String message;
    private LocalDateTime timestamp;
    
    @ManyToOne
    private User performedBy;
    
    public Activity() {
        this.timestamp = LocalDateTime.now();
    }
    
    public Activity(String type, String message, User performedBy) {
        this();
        this.type = type;
        this.message = message;
        this.performedBy = performedBy;
    }
}