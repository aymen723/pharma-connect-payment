package dz.pharmaconnect.pharmaconnectpayment.services;

import java.util.List;
import java.util.Optional;

import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.Auth.Account;
import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.stock.Order;
import org.hibernate.FetchNotFoundException;
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

    public Optional<Payment> get(Long ID) {
        return paymentRepo.findById(ID);
    }

    public Payment createPayment(Order order, Account account) {
        var delivery = 200;
        var tax = 50;


        Invoice invoice = new Invoice(
                account.getUsername(),
                account.getEmail(),
                1.0,
                "https://backend.com/webhook_endpoint",
                "https://frontend.com",
                PaymentMethod.EDAHABIA,
                order.getSecret().toString(),
                order.getPrice() + delivery + tax
        );

        try {

            ChargilyResponse response = client.submitInvoice(invoice);
            if (response.isSuccess()) {
                System.out.println("am here inside the if ");


                response.getStatusCode();

                response.getCheckoutUrl();


                Payment payment = Payment.builder()
                        .orderId(order.getId())
                        .pharmacyId(order.getPharmacy().getId())
                        .userId(order.getAccountId())
                        .Checkoutprice(order.getPrice() + delivery + tax)
                        .comment("test")
                        .deliveryId(order.getDeliveryId())
                        .invoiceNumber(order.getSecret().toString())
                        .paymentStatus(Status.pending)
                        .option(PaymentMethod.EDAHABIA)
                        .dueDate(order.getDate())
                        .checkouturl(response.getCheckoutUrl())
                        .build();
                paymentRepo.save(payment);

                System.out.println(response.getStatusCode());
                System.out.println(response.getCheckoutUrl());

                return payment;
            } else {
                System.out.println("am here inside the eles ");

                response.getStatusCode();
                response.getErrorBody();
                return null;

            }

        } catch (Exception e) {
            System.out.println("am here inside the eles ");

            System.out.println(e.getMessage());
        }

        return null;

    }

    public void deletePayment(Long ID) {
        paymentRepo.deleteById(ID);
    }


    public List<Payment> getUserPayments(Long userId) {
        return paymentRepo.findByUserId(userId);
    }


    public Payment updatePayment(Long paymentId, Payment updatedPayment) {
        return paymentRepo.findById(paymentId).map(existingPayment -> {
            existingPayment.setUserId(updatedPayment.getUserId());
            existingPayment.setPharmacyId(updatedPayment.getPharmacyId());
            existingPayment.setInvoiceNumber(updatedPayment.getInvoiceNumber());
            existingPayment.setDueDate(updatedPayment.getDueDate());
            existingPayment.setCheckoutprice(updatedPayment.getCheckoutprice());
            existingPayment.setCheckouturl(updatedPayment.getCheckouturl());
            existingPayment.setDeliveryId(updatedPayment.getDeliveryId());
            existingPayment.setComment(updatedPayment.getComment());
            existingPayment.setPaymentStatus(updatedPayment.getPaymentStatus());
            existingPayment.setOption(updatedPayment.getOption());

            return paymentRepo.save(existingPayment);
        }).orElseThrow(() -> new FetchNotFoundException("Payment not found with ID: ", paymentId));
    }

}
