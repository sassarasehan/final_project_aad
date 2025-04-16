package lk.ijse.final_project_aad.service;

import lk.ijse.final_project_aad.dto.TaxiDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TaxiService {
    TaxiDTO saveTaxi(TaxiDTO taxiDTO, MultipartFile file);
    TaxiDTO updateTaxi(String taxiId, TaxiDTO taxiDTO, MultipartFile file);
    String generateNextTaxiId();
    void deleteTaxi(String taxiId);
    List<TaxiDTO> getAllTaxis();
    TaxiDTO getTaxiById(String taxiId);

    long countAvailableTaxis();
   /* void markTaxiUnavailable(String taxiId);
    void markTaxiAvailable(String taxiId);*/
}
