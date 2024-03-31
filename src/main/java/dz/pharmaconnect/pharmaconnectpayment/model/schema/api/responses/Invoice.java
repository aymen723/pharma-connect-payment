package dz.pharmaconnect.pharmaconnectpayment.model.schema.api.responses;

import java.time.Instant;

import chargily.epay.java.PaymentMethod;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.entities.enums.Status;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {

    private Integer id;

    private String client;

    private String client_email;

    private Integer invoice_number;

    private Status status;

    private Double amount;

    private Double fee;

    private Double discount;

    private Double due_amount;

    private String comment;

    private PaymentMethod mode;

    private Integer New;

    private Integer tos;

    private String back_url;

    private String invoice_token;

    private Integer api_key_id;

    private Integer meta_data;

    private Instant due_date;

    private Instant created_at;

    private Instant updated_at;

}