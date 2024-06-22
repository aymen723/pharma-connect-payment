package dz.pharmaconnect.pharmaconnectpayment.client;


import dz.pharmaconnect.pharmaconnectpayment.client.config.MicroServiceClientConfig;
import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.Auth.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "PHARMA-CONNECT-AUTH", configuration = MicroServiceClientConfig.class)
public interface AuthClient {


    @GetMapping("/api/v1/accounts/{id}")
    AccountDto getAccount(@PathVariable Long id);


}
