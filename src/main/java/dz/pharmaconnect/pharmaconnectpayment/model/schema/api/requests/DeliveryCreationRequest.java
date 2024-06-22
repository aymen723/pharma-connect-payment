package dz.pharmaconnect.pharmaconnectpayment.model.schema.api.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryCreationRequest {


    @Null
    private Long accountId;


    @NotNull
    private Long orderId;

    @NotNull
    private Long paymentId;

    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
}
