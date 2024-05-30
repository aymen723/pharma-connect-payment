package dz.pharmaconnect.pharmaconnectpayment.model.dto.client.stock;


import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

public class Pharmacy {
    private Integer id;


    private String name;


    private boolean supportPayment = false;


    private Boolean enabled;


    private String picture;


    private String phoneNumber;


    private Long accountId;
}
