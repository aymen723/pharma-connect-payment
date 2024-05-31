package dz.pharmaconnect.pharmaconnectpayment.services;

import java.util.List;

import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.Auth.Account;
import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.stock.Order;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.api.requests.PaymentRequest;
import org.springframework.stereotype.Service;

import chargily.epay.java.ChargilyClient;
import chargily.epay.java.ChargilyResponse;
import chargily.epay.java.Invoice;
import chargily.epay.java.PaymentMethod;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.entities.Payment;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.entities.enums.Status;
import dz.pharmaconnect.pharmaconnectpayment.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepo;

    private final ChargilyClient client = new ChargilyClient(
            "api_T3sOGKfkxKa6SCAgFlR9vWNf4kVKWMTv4RvopHjOXMqeNRWghDL6iNmQk1nkqjKY");

    public List<Payment> getAll() {
        return paymentRepo.findAll();
    }

    public String createPayment(Order order , Account account) {
        var delivery = 200;
        var tax = 50;

        
        Payment payment = Payment.builder()
                .pharmacyId(order.getPharmacy().getId())
                .userId(order.getAccountId())
                .Checkoutprice(order.getPrice()+delivery+tax)
                .comment("test")
                .invoiceNumber(1L)
                .paymentStatus(Status.pending)
                .option(PaymentMethod.EDAHABIA)
                .dueDate(order.getDate())
                .build();


        Invoice invoice = new Invoice(
                account.getUsername(),
                account.getEmail(),
                1.0,
                "https://backend.com/webhook_endpoint",
                "https://frontend.com",
                payment.getOption(),
                payment.getInvoiceNumber().toString(),
                //payment.getCheckoutprice()
                100.0);

        try {

            ChargilyResponse response = client.submitInvoice(invoice);
            if (response.isSuccess()) {
                System.out.println("am here inside the if ");
                paymentRepo.save(payment);
                response.getStatusCode();
                response.getCheckoutUrl();
                System.out.println(response.getStatusCode());
                System.out.println(response.getCheckoutUrl());

                return response.getCheckoutUrl();
            } else {
                System.out.println("am here inside the eles ");

                response.getStatusCode();
                response.getErrorBody();
                return response.getErrorBody();

            }

        } catch (Exception e) {
            System.out.println("am here inside the eles ");

            System.out.println(e.getMessage());
        }

        return "Couldnt create payment";

    }

}
