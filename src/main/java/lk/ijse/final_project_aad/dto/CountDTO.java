package lk.ijse.final_project_aad.dto;

import lombok.Data;

@Data
public class CountDTO {
    private long count;
    
    public CountDTO(long count) {
        this.count = count;
    }
}