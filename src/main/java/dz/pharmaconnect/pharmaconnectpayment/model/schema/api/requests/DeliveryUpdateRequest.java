package dz.pharmaconnect.pharmaconnectpayment.model.schema.api.requests;

import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.delivery.DeliveryStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryUpdateRequest {

    @NotNull
    private Long id;
    private DeliveryStatus status;


    private Long paymentId;
}
