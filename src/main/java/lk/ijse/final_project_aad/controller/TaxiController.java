package lk.ijse.final_project_aad.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ijse.final_project_aad.dto.CountDTO;
import lk.ijse.final_project_aad.dto.TaxiDTO;
import lk.ijse.final_project_aad.entity.Taxi;
import lk.ijse.final_project_aad.repo.TaxiRepository;
import lk.ijse.final_project_aad.service.TaxiService;
import lk.ijse.final_project_aad.service.impl.TaxiServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/taxi")
@CrossOrigin(origins = "*")
public class TaxiController {

    private final TaxiService taxiService;
    private final ObjectMapper objectMapper;
    private final TaxiServiceImpl taxiServiceImpl;
    private final TaxiRepository taxiRepository;

    public TaxiController(TaxiService taxiService, ObjectMapper objectMapper, TaxiServiceImpl taxiServiceImpl, TaxiRepository taxiRepository) {
        this.taxiService = taxiService;
        this.objectMapper = objectMapper;
        this.taxiServiceImpl = taxiServiceImpl;
        this.taxiRepository = taxiRepository;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<TaxiDTO>> getAllTaxis() {
        List<TaxiDTO> taxis = taxiService.getAllTaxis();
        return ResponseEntity.ok(taxis);
    }

    @GetMapping("/nextTaxiId")
    public ResponseEntity<String> getNextTaxiId() {
        return ResponseEntity.ok(taxiService.generateNextTaxiId());
    }

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TaxiDTO> saveTaxi(
            @RequestPart("taxi") String taxiJson,
            @RequestPart(value = "image", required = false) MultipartFile file) {

        try {
            TaxiDTO taxiDTO = objectMapper.readValue(taxiJson, TaxiDTO.class);
            TaxiDTO savedTaxi = taxiService.saveTaxi(taxiDTO, file);
            return ResponseEntity.ok(savedTaxi);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing taxi JSON: " + e.getMessage());
        }
    }

    @PutMapping(value = "/update/{taxiId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TaxiDTO> updateTaxi(
            @PathVariable String taxiId,
            @RequestPart("taxi") String taxiJson,
            @RequestPart(value = "image", required = false) MultipartFile file) {

        try {
            TaxiDTO taxiDTO = objectMapper.readValue(taxiJson, TaxiDTO.class);
            TaxiDTO updatedTaxi = taxiService.updateTaxi(taxiId, taxiDTO, file);
            return ResponseEntity.ok(updatedTaxi);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing taxi JSON: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{taxiId}")
    public ResponseEntity<Void> deleteTaxi(@PathVariable String taxiId) {
        taxiService.deleteTaxi(taxiId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getTaxiId/{taxiId}")
    public ResponseEntity<TaxiDTO> getTaxiById(@PathVariable String taxiId) {
        try {
            TaxiDTO taxi = taxiService.getTaxiById(taxiId);
            return ResponseEntity.ok(taxi);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<CountDTO> getAvailableTaxiCount() {
        return ResponseEntity.ok(new CountDTO(taxiService.countAvailableTaxis()));
    }

    @GetMapping("/available")
    public ResponseEntity<List<TaxiDTO>> getAvailableTaxis() {
        return ResponseEntity.ok(taxiServiceImpl.getAvailableTaxis());
    }

   /* @GetMapping("/available")
    public List<TaxiDTO> getAvailableTaxis() {
        List<Taxi> availableTaxis = taxiRepository.findByIsAvailable("Available");
        return availableTaxis.stream()
                .map(taxi -> modelMapper.map(taxi, TaxiDTO.class))
                .collect(Collectors.toList());
    }*/

   /* @PutMapping("/mark-unavailable/{taxiId}")
    public ResponseEntity<Void> markTaxiUnavailable(@PathVariable String taxiId) {
        taxiService.markTaxiUnavailable(taxiId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/mark-available/{taxiId}")
    public ResponseEntity<Void> markTaxiAvailable(@PathVariable String taxiId) {
        taxiService.markTaxiAvailable(taxiId);
        return ResponseEntity.noContent().build();
    }*/

}
