package dz.pharmaconnect.pharmaconnectpayment.model.dto.client.stock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    private Long id;


    private Long accountId;


    private Pharmacy pharmacy;


    private OrderStatus status;


    private Instant date;


    private Double price;

    private Double checkoutPrice;
    private Double deliveryPrice;
}
