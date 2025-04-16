package lk.ijse.final_project_aad.controller;

import jakarta.validation.Valid;
import lk.ijse.final_project_aad.dto.AuthDTO;
import lk.ijse.final_project_aad.dto.CountDTO;
import lk.ijse.final_project_aad.dto.ResponseDTO;
import lk.ijse.final_project_aad.dto.UserDTO;
import lk.ijse.final_project_aad.repo.UserRepository;
import lk.ijse.final_project_aad.service.UserService;
import lk.ijse.final_project_aad.util.JwtUtil;
import lk.ijse.final_project_aad.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private UserRepository userRepository;


    public UserController(UserService userService, JwtUtil jwtUtil, UserRepository userRepository) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }
    @PostMapping(value = "/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody @Valid UserDTO userDTO) {
        try {
            int res = userService.saveUser(userDTO);
            switch (res) {
                case VarList.Created -> {
                    String token = jwtUtil.generateToken(userDTO);
                    AuthDTO authDTO = new AuthDTO();
                    authDTO.setEmail(userDTO.getEmail());
                    authDTO.setToken(token);
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Success", authDTO));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Email Already Used", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @GetMapping(value = "/get")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("delete/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable("email") String email) {
        try {

            userService.deleteUser(email);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PutMapping("/update/{email}")
    public ResponseEntity<ResponseDTO> updateUser(@PathVariable String email, @RequestBody @Valid UserDTO userDTO) {
        try {
            userDTO.setEmail(email);
            int res = userService.updateUser(userDTO);

            if (res == 1) {
                return ResponseEntity.ok(new ResponseDTO(200, "User updated successfully!", userDTO));
            } else if (res == 0) { // Assuming 0 means not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(404, "User not found!", null));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(400, "Update failed!", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "Internal Server Error", e.getMessage()));
        }
    }

    @GetMapping("/count")
    public ResponseEntity<CountDTO> getUserCount() {
        return ResponseEntity.ok(new CountDTO(userService.countUsers()));
    }


    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        if (!isValidEmail(email)) {
            return ResponseEntity.badRequest().body(false);
        }

        boolean exists = userRepository.existsByEmail(email.toLowerCase().trim());
        return ResponseEntity.ok(exists);
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
    }
}
