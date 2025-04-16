package lk.ijse.final_project_aad.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Taxi {
    @Id
    private String taxiId;
    private String taxiName;
    private String driverName;
    private String licensePlate;
    private String location;
    private String isAvailable;
    private String image;
}
