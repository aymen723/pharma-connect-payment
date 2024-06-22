package dz.pharmaconnect.pharmaconnectpayment.Controllers;

import java.time.Instant;
import java.util.List;

import dz.pharmaconnect.pharmaconnectpayment.client.AuthClient;
import dz.pharmaconnect.pharmaconnectpayment.client.DeliveryClient;
import dz.pharmaconnect.pharmaconnectpayment.client.StockClient;
import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.Auth.AccountDto;
import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.delivery.DeliveryDto;
import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.delivery.DeliveryStatus;
import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.stock.OrderDto;
import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.stock.OrderStatus;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.api.requests.DeliveryCreationRequest;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.api.requests.DeliveryUpdateRequest;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.api.requests.OrderCreationRequest;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.api.requests.OrderUpdateRequest;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.entities.enums.Status;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import org.hibernate.FetchNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

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
    private final DeliveryClient deliveryClient;

    @GetMapping("/{paymentId}")
    @PreAuthorize("hasAnyAuthority('CLIENT','SERVICE', 'PHARMACY')")
    public ResponseEntity<Payment> fetchPayment(@PathVariable Long paymentId, @RequestAttribute(required = false) Long userAccountId) {
        var payment = paymentService.get(paymentId).orElseThrow(() -> new FetchNotFoundException("payment", paymentId));
        var authority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().get().getAuthority();
        if (authority.equals(AccountDto.AccountRole.CLIENT.name()) && !payment.getUserId().equals(userAccountId)) {
            throw new IllegalStateException();
        }
        if (authority.equals(AccountDto.AccountRole.PHARMACY.name())) {
            var pharmacy = stockClient.getPharmacyByOrderId(payment.getOrderId());
            if (!pharmacy.getAccountId().equals(userAccountId)) {
                throw new IllegalStateException();
            }
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


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<Payment> createPayment(@Valid @RequestBody OrderCreationRequest request, @RequestAttribute Long userAccountId) {


        request.setAccountId(userAccountId);

        var order = stockClient.CreateOrder(request);
        var account = authClient.getAccount(userAccountId);
        DeliveryDto delivery = null;
        if (request.getDelivery()
        ) {
            if (request.getY() == null || request.getX() == null) throw new IllegalStateException();

            delivery = deliveryClient.CreateDelivery(DeliveryCreationRequest.builder()
                    .accountId(account.getId())
                    .orderId(order.getId())
                    .latitude(request.getY())
                    .longitude(request.getX())

                    .build());
        }


        Payment res = paymentService.createPayment(order, account, delivery);

        OrderUpdateRequest updateorder = OrderUpdateRequest.builder()
                .id(order.getId())
                .status(OrderStatus.PENDING)
                .deliveryId(res.getDeliveryId())
                .checkoutPrice(res.getCheckoutprice())
                .paymentId(res.getPaymentId())
                .build();

        if (res.getDeliveryId() != null) {
            DeliveryDto updatedDelivery = deliveryClient.putDelivery(DeliveryUpdateRequest.builder()
                    .paymentId(res.getPaymentId()).id(delivery.getId()).status(DeliveryStatus.AWAITING).build());
        }


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
                OrderDto order = stockClient.getOrderById(Long.parseLong(object.getInvoice().getInvoice_number()));
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

                OrderDto order = stockClient.getOrderById(Long.parseLong(object.getInvoice().getInvoice_number()));
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


}
