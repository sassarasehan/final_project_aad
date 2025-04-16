package lk.ijse.final_project_aad.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourPackageDTO {
    private String packageId;
    private String packageName;
    private String description;
    private double price;
    private int duration;
    private String image;
}
