package lk.ijse.final_project_aad.entity;

import jakarta.persistence.*;
import lk.ijse.final_project_aad.config.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;


@Entity
@Table(name = "systemuser")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
    @Column(unique = true)
    private String email;
    private String password;
    private String name;
    private String role;

}