package lk.ijse.final_project_aad.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomBooking {

    @Id
    private String bookingId;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "roomId")
    private Room room;

    private String customerName;
    private String customerEmail;

    private String status;
}
