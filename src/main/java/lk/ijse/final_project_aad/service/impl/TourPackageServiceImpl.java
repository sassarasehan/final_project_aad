package lk.ijse.final_project_aad.service.impl;

import lk.ijse.final_project_aad.dto.TourPackageDTO;
import lk.ijse.final_project_aad.entity.TourPackage;
import lk.ijse.final_project_aad.repo.TourPackageRepository;
import lk.ijse.final_project_aad.service.TourPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourPackageServiceImpl implements TourPackageService {

    @Autowired
    private TourPackageRepository tourPackageRepository;

    @Override
    public String generateNextPackageId() {
        String lastId = tourPackageRepository.findLastPackageId(); // e.g., TO009
        if (lastId == null) {
            return "TO001";
        }
        int num = Integer.parseInt(lastId.substring(2));
        return String.format("TO%03d", num + 1);
    }

    @Override
    public List<TourPackageDTO> getAllPackages() {
        return tourPackageRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TourPackageDTO getPackageById(String packageId) {
        TourPackage tourPackage = tourPackageRepository.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Tour Package not found"));
        return convertToDTO(tourPackage);
    }

    @Override
    public TourPackageDTO createPackage(TourPackageDTO dto, MultipartFile file) {
        TourPackage tourPackage = new TourPackage();
        tourPackage.setPackageId(generateNextPackageId());
        tourPackage.setPackageName(dto.getPackageName());
        tourPackage.setDescription(dto.getDescription());
        tourPackage.setPrice(dto.getPrice());
        tourPackage.setDuration(dto.getDuration());

        String imagePath = saveImage(file);
        tourPackage.setImage(imagePath);

        tourPackageRepository.save(tourPackage);
        return convertToDTO(tourPackage);
    }

    @Override
    public TourPackageDTO updatePackage(String packageId, TourPackageDTO dto, MultipartFile file) {
        TourPackage existing = tourPackageRepository.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Tour Package not found"));

        existing.setPackageName(dto.getPackageName());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setDuration(dto.getDuration());

        if (file != null && !file.isEmpty()) {
            String imagePath = saveImage(file);
            existing.setImage(imagePath);
        }

        tourPackageRepository.save(existing);
        return convertToDTO(existing);
    }

    @Override
    public void deletePackage(String packageId) {
        if (!tourPackageRepository.existsById(packageId)) {
            throw new RuntimeException("Tour Package not found");
        }
        tourPackageRepository.deleteById(packageId);
    }

    private TourPackageDTO convertToDTO(TourPackage entity) {
        return new TourPackageDTO(
                entity.getPackageId(),
                entity.getPackageName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getDuration(),
                entity.getImage()
        );
    }

    private String saveImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String uploadDir = "C:\\Users\\Sehan\\IdeaProjects\\JAVAEE\\final_project_aad\\src\\main\\resources\\static\\uploads";
        File directory = new File(uploadDir);

        if (!directory.exists() && !directory.mkdirs()) {
            throw new RuntimeException("Failed to create upload directory");
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String filePath = uploadDir + File.separator + fileName;

        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image: " + e.getMessage());
        }

        return "/uploads/" + fileName;
    }

    public long countTourPackages() {
        return tourPackageRepository.count();
    }
}
