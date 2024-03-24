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
    // @GeneratedValue(strategy = GeneratedValue.IDENTITY)
    @Column(name = "payment_id")
    private Integer paymentId;

    @Column(name = "payment_user_id")
    private User user;

    @Column(name = "payment_invoice_number")
    private Integer invoiceNumber;

    @Column(name = "payment_date")
    private Instant dueDate;

    @Column(name = "payment_amount")
    private Integer amount;

    @Column(name = "payment_discount")
    private Integer discount;

    @Column(name = "payment_comment")
    private String comment;

    @Column(name = "payment_status")
    private Status paymentStatus;

    @Column(name = "payment_option")
    @Enumerated(EnumType.STRING)
    private PaymentMethod option;

}
