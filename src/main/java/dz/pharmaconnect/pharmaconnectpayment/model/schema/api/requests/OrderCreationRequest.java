package dz.pharmaconnect.pharmaconnectpayment.model.schema.api.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class OrderCreationRequest {



        @Null
        private Long accountId;

        @NotNull
        private Integer pharmacyId;

        @NotEmpty
        @Valid
        private List<PurchaseRequest> products;


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
