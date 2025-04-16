package lk.ijse.final_project_aad.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    private String roomId;

    private String roomType;
    private double price;
    private String available;
    private String image;

    @ManyToOne
    @JoinColumn(name = "hotel_id", referencedColumnName = "hotelId")
    private Hotel hotel;
}
