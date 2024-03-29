package dz.pharmaconnect.pharmaconnectpayment.Controllers;

import java.util.List;

import chargily.epay.java.Invoice;
import chargily.epay.java.PaymentMethod;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import chargily.epay.java.*;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.api.requests.PaymentRequest;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.entities.Payment;
import dz.pharmaconnect.pharmaconnectpayment.services.PaymentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/test")
    public String test() {
        return "hello";
    }

    @GetMapping("/getPayments")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAll();
        return ResponseEntity.ok(payments);
    }

    @PostMapping("/createPayment")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPayment(@RequestBody PaymentRequest paymentRequest) {
        paymentService.createPayment(paymentRequest);

    }

}
