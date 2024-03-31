package dz.pharmaconnect.pharmaconnectpayment.services;

import java.util.List;

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

    public String createPayment(PaymentRequest paymentRequest) {
        Payment payment = Payment.builder()
                .pharmacyId(paymentRequest.getPharmacyId())
                .userId(paymentRequest.getUserId())
                .amount(paymentRequest.getCheckoutPrice())
                .comment("test")
                .discount(5.0)
                .invoiceNumber(1L)
                .paymentStatus(Status.PENDING)
                .option(PaymentMethod.EDAHABIA)
                .dueDate(paymentRequest.getOrderDate())
                .build();

        paymentRepo.save(payment);

        Invoice invoice = new Invoice(
                "Chakhoum Ahmed",
                "rainxh11@gmail.com",
                payment.getDiscount(),
                "https://backend.com/webhook_endpoint",
                "https://frontend.com",
                payment.getOption(),
                payment.getInvoiceNumber().toString(),
                payment.getAmount());

        try {

            ChargilyResponse response = client.submitInvoice(invoice);
            if (response.isSuccess()) {
                System.out.println("am here inside the if ");
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
