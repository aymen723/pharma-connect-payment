package dz.pharmaconnect.pharmaconnectpayment;


import dz.pharmaconnect.pharmaconnectpayment.model.dto.PaymentDto;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.entities.Payment;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.entities.enums.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;


public class DtoTests {
    @Test
    public void paymentDtoMappingTest() {
        Payment payment = new Payment();
        payment.setPaymentId(1L);
        payment.setUserId(1001L);
        payment.setPharmacyId(123);
        payment.setInvoiceNumber("INV-001");
        payment.setDueDate(Instant.now().plusSeconds(3600));
        payment.setCheckoutprice(5000.0);
        payment.setCheckouturl("https://example.com/checkout");
        payment.setDeliveryId(2002L);
        payment.setDeliveryPrice(5.0);
        payment.setTransactionFee(2.0);
        payment.setComment("Payment comment");
        payment.setOrderId(3003L);
        payment.setPaymentStatus(Status.paid);


        PaymentDto paymentDto = PaymentDto.map(payment);

        Assertions.assertNotNull(paymentDto);
        Assertions.assertEquals(payment.getPaymentId(), paymentDto.getPaymentId());
        Assertions.assertEquals(payment.getUserId(), paymentDto.getUserId());
        Assertions.assertEquals(payment.getPharmacyId(), paymentDto.getPharmacyId());
        Assertions.assertEquals(payment.getInvoiceNumber(), paymentDto.getInvoiceNumber());
        Assertions.assertEquals(payment.getDueDate(), paymentDto.getDueDate());

        Assertions.assertEquals(payment.getCheckouturl(), paymentDto.getCheckouturl());
        Assertions.assertEquals(payment.getDeliveryId(), paymentDto.getDeliveryId());
        Assertions.assertEquals(payment.getDeliveryPrice(), paymentDto.getDeliveryPrice());
        Assertions.assertEquals(payment.getTransactionFee(), paymentDto.getTransactionFee());
        Assertions.assertEquals(payment.getComment(), paymentDto.getComment());
        Assertions.assertEquals(payment.getOrderId(), paymentDto.getOrderId());
        Assertions.assertEquals(payment.getPaymentStatus(), paymentDto.getPaymentStatus());
        Assertions.assertEquals(payment.getOption(), paymentDto.getOption());
    }

    @Test
    public void paymentDtoMappingNullTest() {
        PaymentDto paymentDto = PaymentDto.map(null);
        Assertions.assertNull(paymentDto);
    }

}
