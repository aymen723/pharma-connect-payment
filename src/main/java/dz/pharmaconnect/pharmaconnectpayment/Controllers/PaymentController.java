package dz.pharmaconnect.pharmaconnectpayment.Controllers;

import java.time.Instant;
import java.util.List;

import chargily.epay.java.PaymentMethod;
import dz.pharmaconnect.pharmaconnectpayment.client.AuthClient;
import dz.pharmaconnect.pharmaconnectpayment.client.StockClient;
import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.Auth.Account;
import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.stock.Order;
import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.stock.OrderStatus;
import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.stock.Pharmacy;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.api.requests.OrderCreationRequest;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.api.requests.OrderUpdateRequest;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.entities.enums.Status;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import org.hibernate.FetchNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
@CrossOrigin()
public class PaymentController {

    private final PaymentService paymentService;
    private final StockClient stockClient;
    private final AuthClient authClient;

    @GetMapping("/{paymentId}")
    @PreAuthorize("hasAnyAuthority('CLIENT','SERVICE')")
    public ResponseEntity<Payment> fetchPayment(@PathVariable Long paymentId, @RequestAttribute(required = false) Long userAccountId) {
        var payment = paymentService.get(paymentId).orElseThrow(() -> new FetchNotFoundException("payment", paymentId));
        var authority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().get().getAuthority();
        if (authority.equals(Account.AccountRole.CLIENT.name()) && !payment.getUserId().equals(userAccountId)) {
            throw new IllegalStateException();
        }
        return ResponseEntity.ok(payment);
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
    public ResponseEntity<Payment> createPayment(@Valid @RequestBody OrderCreationRequest request, @RequestAttribute Long userAccountId) {


        request.setAccountId(userAccountId);

        var Order = stockClient.CreateOrder(request);
        var account = authClient.getAccount(userAccountId);

        Payment res = paymentService.createPayment(Order, account);

        OrderUpdateRequest updateorder = OrderUpdateRequest.builder()
                .id(Order.getId())
                .status(OrderStatus.PENDING)
                .deliveryId(res.getDeliveryId())
                .checkoutPrice(res.getCheckoutprice())
                .paymentId(res.getPaymentId())
                .build();


        stockClient.patchOrder(updateorder);
        return ResponseEntity.ok(res);

    }

    @PostMapping("/webhook")
    @Consumes(MediaType.ALL_VALUE)
    @ResponseStatus
    @PreAuthorize("permitAll()")
    public ResponseEntity<Payment> UpdatePayment(@RequestBody String res) {

        ObjectMapper objectmapper = new ObjectMapper();

        try {

            InvoiceResponse object = objectmapper.readValue(res, InvoiceResponse.class);

            if (object.getInvoice().getStatus() == Status.paid) {
                Order order = stockClient.getOrderById(Long.parseLong(object.getInvoice().getInvoice_number()));
                OrderUpdateRequest updateorder = OrderUpdateRequest.builder()
                        .id(order.getId())
                        .status(OrderStatus.PAID)
                        .deliveryId(order.getDeliveryId())
                        .checkoutPrice(object.getInvoice().getDue_amount())

                        .build();

                stockClient.patchOrder(updateorder);

                Instant duedate = Instant.now();


                Payment payment = Payment.builder()
                        .paymentId(order.getId())
                        .pharmacyId(order.getPharmacy().getId())
                        .userId(order.getAccountId())
                        .Checkoutprice(object.getInvoice().getDue_amount())
                        .comment("test")
                        .deliveryId(order.getDeliveryId())
                        .invoiceNumber(object.getInvoice().getInvoice_number())
                        .paymentStatus(Status.paid)
                        .option(object.getInvoice().getMode())
                        .dueDate(duedate)
                        .build();
                return ResponseEntity.ok(paymentService.updatePayment(payment.getPaymentId(), payment));
            } else if (object.getInvoice().getStatus() == Status.failed) {

                Order order = stockClient.getOrderById(Long.parseLong(object.getInvoice().getInvoice_number()));
                OrderUpdateRequest updateorder = OrderUpdateRequest.builder()
                        .id(order.getId())
                        .status(OrderStatus.CANCELED)
                        .deliveryId(order.getDeliveryId())
                        .checkoutPrice(object.getInvoice().getDue_amount())

                        .build();

                stockClient.patchOrder(updateorder);

                Instant duedate = Instant.now();


                Payment payment = Payment.builder()
                        .paymentId(order.getId())
                        .pharmacyId(order.getPharmacy().getId())
                        .userId(order.getAccountId())
                        .Checkoutprice(object.getInvoice().getDue_amount())
                        .comment("test")
                        .deliveryId(order.getDeliveryId())
                        .invoiceNumber(object.getInvoice().getInvoice_number())
                        .paymentStatus(Status.failed)
                        .option(object.getInvoice().getMode())
                        .dueDate(duedate)
                        .build();
                return ResponseEntity.ok(paymentService.updatePayment(payment.getPaymentId(), payment));
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());

            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(null);

    }

    @GetMapping("/{UserId}/User")
    @PermitAll
    public ResponseEntity<List<Payment>> fetchUserpayment(@PathVariable Long paymentId) {
        return ResponseEntity.ok(paymentService.getUserPayments(paymentId));
    }

}
