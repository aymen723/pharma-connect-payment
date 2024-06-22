package dz.pharmaconnect.pharmaconnectpayment.model.dto;

import chargily.epay.java.PaymentMethod;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.entities.enums.Status;
import jakarta.persistence.*;

import java.time.Instant;

public class PaymentDto {


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

    @Column(name = "checkout_url")
    private String checkouturl;

    // @Column(name = "payment_discount")
    // private Double discount;

    @Column(name = "payment_delivery_id")
    private Long deliveryId;


    @Column(name = "payment_delivery_price")
    private Double deliveryPrice;

    @Column(name = "transactionFee")
    private Double transactionFee;

    @Column(name = "payment_comment")
    private String comment;


    @Column(name = "payment_order_id")
    private Long orderId;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private Status paymentStatus;

    @Column(name = "payment_option")
    @Enumerated(EnumType.STRING)
    private PaymentMethod option;
}
