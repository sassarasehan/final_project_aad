package lk.ijse.final_project_aad.repo;

import lk.ijse.final_project_aad.entity.Activity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findTopNByOrderByTimestampDesc(Pageable pageable);

    default List<Activity> findTopNByOrderByTimestampDesc(int limit) {
        return findTopNByOrderByTimestampDesc(Pageable.ofSize(limit));
    }
}