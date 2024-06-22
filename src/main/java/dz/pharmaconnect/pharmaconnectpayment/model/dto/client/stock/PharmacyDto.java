package dz.pharmaconnect.pharmaconnectpayment.model.dto.client.stock;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PharmacyDto {
    private Integer id;


    private String name;


    private boolean supportPayment = false;


    private Boolean enabled;


    private String picture;


    private String phoneNumber;


    private Long accountId;
}
