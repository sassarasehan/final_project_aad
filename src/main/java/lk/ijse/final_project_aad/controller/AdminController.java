package lk.ijse.final_project_aad.controller;

import lk.ijse.final_project_aad.config.VarList;
import lk.ijse.final_project_aad.dto.DashboardDataDTO;
import lk.ijse.final_project_aad.dto.ResponseDTO;
import lk.ijse.final_project_aad.repo.UserRepository;
import lk.ijse.final_project_aad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("api/v1/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/test1")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String check(){
        return "passed~!1";
    }

    @GetMapping("/test2")
    @PreAuthorize("hasAuthority('USER')")
    public String checks(){
        return "passed~!2";
    }

    @GetMapping("/test3")
    @PreAuthorize("hasAuthority('USER')")
    public String checkss(){
        return "passed~!2";
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> getDashboardData() {
        long totalUsers = userService.getTotalUsers();
        DashboardDataDTO dashboardData = new DashboardDataDTO(totalUsers);

        return ResponseEntity.ok(new ResponseDTO(VarList.Success, "Dashboard Data", dashboardData));
    }

}
