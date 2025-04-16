package lk.ijse.final_project_aad.repo;

import lk.ijse.final_project_aad.entity.Taxi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxiRepository extends JpaRepository<Taxi, String> {
    @Query("SELECT t.taxiId FROM Taxi t ORDER BY t.taxiId DESC")
    String findLastTaxiId();

    long countByIsAvailable(String available);

    List<Taxi> findAllByIsAvailable(String isAvailable);

    List<Taxi> findByIsAvailable(String available);
}
