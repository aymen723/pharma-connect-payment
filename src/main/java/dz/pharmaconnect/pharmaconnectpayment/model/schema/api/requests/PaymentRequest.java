package dz.pharmaconnect.pharmaconnectpayment.model.schema.api.requests;

import java.time.Instant;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    @NonNull
    private Integer orderid;

    @NonNull
    private Integer pharmacyId;

    @NonNull
    private Integer checkoutPrice;

    @NonNull
    private Integer userId;

    private Instant orderDate;

    private Integer orderPrice;

}
