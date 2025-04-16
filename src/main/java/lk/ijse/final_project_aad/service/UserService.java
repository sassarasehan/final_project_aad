package lk.ijse.final_project_aad.service;


import lk.ijse.final_project_aad.dto.UserDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    int saveUser(UserDTO userDTO);
    UserDTO searchUser(String username);

    List<UserDTO> getAllUsers();

    void deleteUser(String email);

    int updateUser(UserDTO userDTO);

    long getTotalUsers();

    long countUsers();
}