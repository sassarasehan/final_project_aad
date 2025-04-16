package lk.ijse.final_project_aad.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    @Id

    private String hotelId;
    private String name;
    private String address;
    private String phoneNumber;
    private String image;

    public Hotel(String hotelId) {
        this.hotelId = hotelId;
    }
}