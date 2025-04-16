package lk.ijse.final_project_aad.repo;

import lk.ijse.final_project_aad.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, String> {

}