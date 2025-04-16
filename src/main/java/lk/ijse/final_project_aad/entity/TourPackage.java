package lk.ijse.final_project_aad.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "tour_package_seq", sequenceName = "tour_package_sequence", allocationSize = 1)
public class TourPackage {
    @Id
    private String packageId;
    
    private String packageName;
    private String description;
    private double price;
    private int duration;
    private String image;
}
