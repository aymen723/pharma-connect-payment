package dz.pharmaconnect.pharmaconnectpayment.model.schema.api.responses;

import java.time.Instant;
import java.util.Date;

import chargily.epay.java.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.entities.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Invoice {

    private Integer id;

    private String client;

    private String client_email;

    private String invoice_number;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Double amount;

    private Double fee;

    private Integer discount;

    private Double due_amount;

    private String comment;

    private PaymentMethod mode;

    @JsonProperty(value = "new")
    private Integer New;
    //
    private Integer tos;

    private String back_url;
    //
    private String invoice_token;
    //
    private Integer api_key_id;
    //
    private Integer meta_data;

   private String due_date;

    private String created_at;

    private String updated_at;
}