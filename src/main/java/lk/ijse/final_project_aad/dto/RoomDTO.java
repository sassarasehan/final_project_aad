package lk.ijse.final_project_aad.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomDTO {
    private String roomId;
    private String roomType;
    private double price;
    private String available;
    private String hotelId;
    private String image;
}
