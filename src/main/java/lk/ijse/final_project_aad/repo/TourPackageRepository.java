package lk.ijse.final_project_aad.repo;

import lk.ijse.final_project_aad.dto.TaxiDTO;
import lk.ijse.final_project_aad.entity.TourPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TourPackageRepository extends JpaRepository<TourPackage, String> {
    @Query(value = "SELECT package_id FROM tour_package ORDER BY package_id DESC LIMIT 1", nativeQuery = true)
    String findLastPackageId();

    @Override
    long count();
}
