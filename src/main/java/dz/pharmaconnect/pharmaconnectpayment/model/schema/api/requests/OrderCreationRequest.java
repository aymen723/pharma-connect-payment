package dz.pharmaconnect.pharmaconnectpayment.model.schema.api.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class OrderCreationRequest {


    @Null
    private Long accountId;

    @NotNull
    private Integer pharmacyId;

    @NotEmpty
    @Valid
    private List<PurchaseRequest> products;

    @NotNull
    private Boolean delivery;


    private Double x;
    private Double y;


    @Data
    public static class PurchaseRequest {
        @NotNull
        private Integer productId;

        @NotNull
        @Max(5)
        @Min(1)
        private Integer count;
    }


}
