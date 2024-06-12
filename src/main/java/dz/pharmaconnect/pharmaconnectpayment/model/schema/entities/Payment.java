package dz.pharmaconnect.pharmaconnectpayment.model.schema.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import chargily.epay.java.PaymentMethod;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.entities.enums.Status;

@Entity
@Table(name = "payments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "payment_user_id")
    private Long userId;

    @Column(name = "payment_pharmacy_id")
    private Integer pharmacyId;

    @Column(name = "payment_invoice_number")
    private String invoiceNumber;

    @Column(name = "payment_date")
    private Instant dueDate;

    @Column(name = "checkout_price")
    private Double Checkoutprice;

    @Column(name="checkout_url")
    private  String checkouturl;

   // @Column(name = "payment_discount")
   // private Double discount;

    @Column(name="delivery_id")
    private  Long deliveryId;

    @Column(name = "payment_comment")
    private String comment;




    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private Status paymentStatus;

    @Column(name = "payment_option")
    @Enumerated(EnumType.STRING)
    private PaymentMethod option;

}
