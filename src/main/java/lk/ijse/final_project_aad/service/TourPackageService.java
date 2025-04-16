package lk.ijse.final_project_aad.service;

import lk.ijse.final_project_aad.dto.TourPackageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TourPackageService {
    List<TourPackageDTO> getAllPackages();
    TourPackageDTO getPackageById(String packageId);
    TourPackageDTO createPackage(TourPackageDTO dto, MultipartFile file);
    TourPackageDTO updatePackage(String packageId, TourPackageDTO dto, MultipartFile file);

    void deletePackage(String packageId);

    String generateNextPackageId();

    long countTourPackages();
}
