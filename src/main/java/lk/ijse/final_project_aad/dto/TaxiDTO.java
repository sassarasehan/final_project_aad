package lk.ijse.final_project_aad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxiDTO {
    private String taxiId;
    private String taxiName;
    private String driverName;
    private String licensePlate;
    private String location;
    private String isAvailable;
    private String image;
}
