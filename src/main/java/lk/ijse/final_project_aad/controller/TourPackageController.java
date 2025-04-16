package lk.ijse.final_project_aad.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ijse.final_project_aad.dto.CountDTO;
import lk.ijse.final_project_aad.dto.TaxiDTO;
import lk.ijse.final_project_aad.dto.TourPackageDTO;
import lk.ijse.final_project_aad.repo.TourPackageRepository;
import lk.ijse.final_project_aad.service.TourPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tour-packages")
@RequiredArgsConstructor
@CrossOrigin
public class TourPackageController {

    private final TourPackageService tourPackageService;
    private final ObjectMapper objectMapper;
    private final TourPackageRepository tourPackageRepository;

    @GetMapping("/getAll")
    public ResponseEntity<List<TourPackageDTO>> getAllPackages() {
        return ResponseEntity.ok(tourPackageService.getAllPackages());
    }

    @GetMapping("/{packageId}")
    public ResponseEntity<TourPackageDTO> getPackageById(@PathVariable String packageId) {
        return ResponseEntity.ok(tourPackageService.getPackageById(packageId));
    }

    @GetMapping("/nextPackageId")
    public ResponseEntity<String> getNextPackageId() {
        return ResponseEntity.ok(tourPackageService.generateNextPackageId());
    }

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TourPackageDTO> createPackage(
            @RequestPart("package") String packageJson,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        try {
            TourPackageDTO dto = objectMapper.readValue(packageJson, TourPackageDTO.class);
            return ResponseEntity.ok(tourPackageService.createPackage(dto, imageFile));
        } catch (Exception e) {
            throw new RuntimeException("Error parsing TourPackage JSON: " + e.getMessage());
        }
    }

    @PutMapping(value = "/update/{packageId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TourPackageDTO> updatePackage(
            @PathVariable String packageId,
            @RequestPart("package") String packageJson,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        try {
            TourPackageDTO dto = objectMapper.readValue(packageJson, TourPackageDTO.class);
            return ResponseEntity.ok(tourPackageService.updatePackage(packageId, dto, imageFile));
        } catch (Exception e) {
            throw new RuntimeException("Error parsing TourPackage JSON: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{packageId}")
    public ResponseEntity<Void> deletePackage(@PathVariable String packageId) {
        tourPackageService.deletePackage(packageId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<CountDTO> getTourPackageCount() {
        return ResponseEntity.ok(new CountDTO(tourPackageService.countTourPackages()));
    }


}
