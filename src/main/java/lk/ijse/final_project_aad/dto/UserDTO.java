package lk.ijse.final_project_aad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private String email;
    private String password;
    private String name;
    private String role;

    public UserDTO(UUID uid, String email, String role) {
        this.email = email;
        this.role = role;
        this.password = UUID.randomUUID().toString();
    }

    public UserDTO(UUID uid, String email, String role, String name) {
        this.email = email;
        this.role = role;
        this.name = name;
        this.password = UUID.randomUUID().toString();
    }
}
