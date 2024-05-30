package dz.pharmaconnect.pharmaconnectpayment.Controllers;

import java.util.List;

import dz.pharmaconnect.pharmaconnectpayment.client.StockClient;
import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.stock.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dz.pharmaconnect.pharmaconnectpayment.model.schema.api.requests.PaymentRequest;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.api.responses.Invoice;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.api.responses.InvoiceResponse;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.entities.Payment;
import dz.pharmaconnect.pharmaconnectpayment.services.PaymentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final StockClient stockClient;

    @GetMapping("/test")
    public ResponseEntity<Order> test() {
        return ResponseEntity.ok(stockClient.getOrderById(1L));
    }

    @GetMapping("/getPayments")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAll();
        return ResponseEntity.ok(payments);
    }


    @PostMapping("/createPayment")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequest paymentRequest) {
        String url = paymentService.createPayment(paymentRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(url);
    }

    @PostMapping("/status")
    public ResponseEntity<String> status(@RequestBody Invoice object) {

        System.out.println(object.getClient());
        // ObjectMapper mapper = new ObjectMapper();
        // try {
        // ConfirmationResponse obj = mapper.readValue(object,
        // ConfirmationResponse.class);
        // System.out.println(obj.getClient());

        // } catch (JsonProcessingException e) {
        // System.out.println(e.getMessage());
        // }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("worked");
    }
}