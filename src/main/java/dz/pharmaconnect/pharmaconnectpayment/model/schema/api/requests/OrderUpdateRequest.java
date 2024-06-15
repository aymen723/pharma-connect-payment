package dz.pharmaconnect.pharmaconnectpayment.model.schema.api.requests;

import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.stock.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderUpdateRequest {

    @NotNull
    private Long id;
    private OrderStatus status;
    private Double checkoutPrice;

    private Long paymentId;


    private Long deliveryId;


}
