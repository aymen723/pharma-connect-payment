package dz.pharmaconnect.pharmaconnectpayment.Controllers;

import java.util.List;

import dz.pharmaconnect.pharmaconnectpayment.client.AuthClient;
import dz.pharmaconnect.pharmaconnectpayment.client.StockClient;
import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.Auth.Account;
import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.stock.Order;
import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.stock.Pharmacy;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.api.requests.OrderCreationRequest;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("isAuthenticated()")
public class PaymentController {

    private final PaymentService paymentService;
    private final StockClient stockClient;
    private final AuthClient authClient;

    @GetMapping("/test/{orderId}")
    @PermitAll
    public ResponseEntity<Order> test(@PathVariable Long orderId) {
        return ResponseEntity.ok(stockClient.getOrderById(orderId));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAll();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/test")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> testS() {
        return ResponseEntity.ok("account id = " + "userAccountId");
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<String> createPayment(@Valid @RequestBody OrderCreationRequest request, @RequestAttribute Long userAccountId) {


        request.setAccountId(userAccountId);

        var Order = stockClient.CreateOrder(request);
        var account = authClient.getAccount(userAccountId);

        String url = paymentService.createPayment(Order, account);

        return ResponseEntity.ok(url);
    }

    @PostMapping("/webhook")
    @ResponseStatus
    @PreAuthorize("permitAll()")
    public String UpdatePayment(@RequestBody InvoiceResponse res) {


        return "nice ya rkhiss";
    }


    @PostMapping("/status")
    @PreAuthorize("permitAll()")
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