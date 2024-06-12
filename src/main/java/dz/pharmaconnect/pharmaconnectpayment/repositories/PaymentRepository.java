package dz.pharmaconnect.pharmaconnectpayment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dz.pharmaconnect.pharmaconnectpayment.model.schema.entities.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
