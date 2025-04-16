package lk.ijse.final_project_aad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class ResponseDTO {
    private int code;
    private String message;
    private Object data;

    public ResponseDTO(String success, String dashboardData, DashboardDataDTO dashboardData1) {
        this.code = 200;
        this.message = success;
        this.data = dashboardData;
    }
}
