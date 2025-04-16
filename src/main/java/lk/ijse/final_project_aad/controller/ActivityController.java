package lk.ijse.final_project_aad.controller;

import lk.ijse.final_project_aad.dto.ActivityDTO;
import lk.ijse.final_project_aad.service.ActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/activity")
@CrossOrigin
public class ActivityController {
    
    private final ActivityService activityService;
    
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }
    
    @GetMapping
    public ResponseEntity<List<ActivityDTO>> getRecentActivity() {
        return ResponseEntity.ok(activityService.getRecentActivity());
    }
}