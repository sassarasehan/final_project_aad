package lk.ijse.final_project_aad.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    private String bookingId;

    @ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "roomId")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "taxi_id", referencedColumnName = "taxiId")
    private Taxi taxi;

    @ManyToOne
    @JoinColumn(name = "package_id", referencedColumnName = "packageId")
    private TourPackage tourPackage;

    @ManyToOne
    @JoinColumn(name = "hotel_id", referencedColumnName = "hotelId")
    private Hotel hotel;

    private LocalDateTime bookingDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int guestCount;
    private String status;
    private String bookingType;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Payment payment;

    private boolean resourceMarkedAvailable = false;
    private double amount;

}