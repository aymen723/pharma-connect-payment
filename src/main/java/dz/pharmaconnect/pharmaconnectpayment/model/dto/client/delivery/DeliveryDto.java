package dz.pharmaconnect.pharmaconnectpayment.model.dto.client.delivery;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;


@Data
public class DeliveryDto {


    private Long id;


    private UUID encode;


    private Long orderId;


    private Long paymentId;


    private Instant creationDate;


    private Instant deliveredDate;


    private DeliveryStatus status;


}


