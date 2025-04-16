package lk.ijse.final_project_aad.service.impl;

import lk.ijse.final_project_aad.dto.TaxiDTO;
import lk.ijse.final_project_aad.entity.Taxi;
import lk.ijse.final_project_aad.repo.TaxiRepository;
import lk.ijse.final_project_aad.service.TaxiService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaxiServiceImpl implements TaxiService {

    @Autowired
    private TaxiRepository taxiRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String generateNextTaxiId() {
        String lastId = taxiRepository.findLastTaxiId();
        if (lastId == null) {
            return "T001";
        }
        int nextIdNum = Integer.parseInt(lastId.substring(1)) + 1;
        return String.format("T%03d", nextIdNum);
    }

    @Override
    public List<TaxiDTO> getAllTaxis() {
        return taxiRepository.findAll().stream()
                .map(taxi -> new TaxiDTO(
                        taxi.getTaxiId(),
                        taxi.getTaxiName(),
                        taxi.getDriverName(),
                        taxi.getLicensePlate(),
                        taxi.getLocation(),
                        taxi.getIsAvailable(),
                        taxi.getImage()
                )).collect(Collectors.toList());
    }

    @Override
    public TaxiDTO getTaxiById(String taxiId) {
        Taxi taxi = taxiRepository.findById(taxiId)
                .orElseThrow(() -> new RuntimeException("Taxi not found"));
        return new TaxiDTO(
                taxi.getTaxiId(),
                taxi.getTaxiName(),
                taxi.getDriverName(),
                taxi.getLicensePlate(),
                taxi.getLocation(),
                taxi.getIsAvailable(),
                taxi.getImage()
        );
    }

    @Override
    public TaxiDTO saveTaxi(TaxiDTO taxiDTO, MultipartFile file) {
        Taxi taxi = new Taxi();
        taxi.setTaxiId(generateNextTaxiId());
        taxi.setTaxiName(taxiDTO.getTaxiName());
        taxi.setDriverName(taxiDTO.getDriverName());
        taxi.setLicensePlate(taxiDTO.getLicensePlate());
        taxi.setLocation(taxiDTO.getLocation());
        taxi.setIsAvailable(taxiDTO.getIsAvailable());

        String imagePath = saveImage(file);
        taxi.setImage(imagePath);

        taxiRepository.save(taxi);
        return new TaxiDTO(taxi.getTaxiId(), taxi.getTaxiName(), taxi.getDriverName(), taxi.getLicensePlate(),
                taxi.getLocation(), taxi.getIsAvailable(), taxi.getImage());
    }

    @Override
    public TaxiDTO updateTaxi(String taxiId, TaxiDTO taxiDTO, MultipartFile file) {
        Taxi taxi = taxiRepository.findById(taxiId)
                .orElseThrow(() -> new RuntimeException("Taxi not found"));

        taxi.setTaxiName(taxiDTO.getTaxiName());
        taxi.setDriverName(taxiDTO.getDriverName());
        taxi.setLicensePlate(taxiDTO.getLicensePlate());
        taxi.setLocation(taxiDTO.getLocation());
        taxi.setIsAvailable(taxiDTO.getIsAvailable());

        if (file != null && !file.isEmpty()) {
            String imagePath = saveImage(file);
            taxi.setImage(imagePath);
        }

        taxiRepository.save(taxi);
        return new TaxiDTO(taxi.getTaxiId(), taxi.getTaxiName(), taxi.getDriverName(), taxi.getLicensePlate(),
                taxi.getLocation(), taxi.getIsAvailable(), taxi.getImage());
    }

    @Override
    public void deleteTaxi(String taxiId) {
        taxiRepository.deleteById(taxiId);
    }

    private String saveImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String uploadDir = "C:\\Users\\Sehan\\IdeaProjects\\JAVAEE\\final_project_aad\\src\\main\\resources\\static\\uploads";
        File directory = new File(uploadDir);

        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                throw new RuntimeException("Failed to create upload directory");
            }
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String filePath = uploadDir + "/" + fileName;

        try {
            File saveFile = new File(filePath);
            file.transferTo(saveFile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image: " + e.getMessage());
        }

        return "/uploads/" + fileName;
    }

    public long countAvailableTaxis() {
        return taxiRepository.countByIsAvailable("available");
    }

    public List<TaxiDTO> getAvailableTaxis() {
        List<Taxi> availableTaxis = taxiRepository.findAllByIsAvailable("AVAILABLE");
        return availableTaxis.stream()
                .map(taxi -> modelMapper.map(taxi, TaxiDTO.class))
                .collect(Collectors.toList());
    }

   /* @Override
    public void markTaxiUnavailable(String taxiId) {
        Taxi taxi = taxiRepository.findById(taxiId)
                .orElseThrow(() -> new RuntimeException("Taxi not found"));
        taxi.setIsAvailable("unavailable");
        taxiRepository.save(taxi);
    }

    @Override
    public void markTaxiAvailable(String taxiId) {
        Taxi taxi = taxiRepository.findById(taxiId)
                .orElseThrow(() -> new RuntimeException("Taxi not found"));
        taxi.setIsAvailable("available");
        taxiRepository.save(taxi);
    }*/

}
