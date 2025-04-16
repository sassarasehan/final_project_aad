package lk.ijse.final_project_aad.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDataDTO {
    private long totalUsers;
    private String loggedInUserName;

    public DashboardDataDTO(long totalUsers) {
        this.totalUsers = totalUsers;
    }
}