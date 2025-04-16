package lk.ijse.final_project_aad.service.impl;

import lk.ijse.final_project_aad.dto.ActivityDTO;
import lk.ijse.final_project_aad.entity.Activity;
import lk.ijse.final_project_aad.entity.User;

import lk.ijse.final_project_aad.repo.ActivityRepository;
import lk.ijse.final_project_aad.service.ActivityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private static final int DEFAULT_ACTIVITY_LIMIT = 5;

    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityDTO> getRecentActivity() {
        return getRecentActivity(DEFAULT_ACTIVITY_LIMIT);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityDTO> getRecentActivity(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("Limit must be positive");
        }

        return activityRepository.findTopNByOrderByTimestampDesc(limit).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void logActivity(String type, String message, User user) {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Activity type cannot be null or empty");
        }

        Activity activity = new Activity();
        activity.setType(type.trim());
        activity.setMessage(message != null ? message.trim() : "");
        activity.setPerformedBy(user);
        activity.setTimestamp(LocalDateTime.now());

        activityRepository.save(activity);
    }

    @Override
    public void logSystemActivity(String type, String message) {
        logActivity(type, message, null);
    }

    private ActivityDTO convertToDTO(Activity activity) {
        return new ActivityDTO(
                activity.getType(),
                activity.getMessage(),
                activity.getTimestamp(),
                activity.getPerformedBy() != null ?
                        activity.getPerformedBy().getEmail() : "SYSTEM"
        );
    }
}