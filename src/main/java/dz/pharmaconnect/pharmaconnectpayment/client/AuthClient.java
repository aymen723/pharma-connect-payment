package dz.pharmaconnect.pharmaconnectpayment.client;


import dz.pharmaconnect.pharmaconnectpayment.client.config.MicroServiceClientConfig;
import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.Auth.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "PHARMA-CONNECT-AUTH", configuration = MicroServiceClientConfig.class)
public interface AuthClient {


    @GetMapping("/api/v1/accounts/{id}")
    Account getAccount(@PathVariable Long id);

    @GetMapping("/api/v1/accounts")
    List<Account> getAccounts();


}
