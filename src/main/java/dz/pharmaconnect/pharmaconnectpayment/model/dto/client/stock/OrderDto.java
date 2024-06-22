package dz.pharmaconnect.pharmaconnectpayment.model.dto.client.stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDto {

    private Long id;


    private Long accountId;

    private UUID secret;


    private PharmacyDto pharmacy;


    private OrderStatus status;


    private Instant date;

    private Long deliveryId;

    private Double price;

    private Double checkoutPrice;
    private Double deliveryPrice;
}
