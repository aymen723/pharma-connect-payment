package dz.pharmaconnect.pharmaconnectpayment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dz.pharmaconnect.pharmaconnectpayment.model.schema.entities.Payment;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUserId(Long userId);

    @Query("select p from Payment p " +
            "where p.orderId = :orderId")
    Optional<Payment> findByOrderId(Long orderId);

}
