package dz.pharmaconnect.pharmaconnectpayment.model.schema.api.requests;

import java.time.Instant;
import java.util.List;

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
    private Integer pharmacyId;

    @NonNull
    private List<Integer> prodcutsId;

    @NonNull
    private Integer userId;

    private Instant orderDate;


}
