package dz.pharmaconnect.pharmaconnectpayment.client;

import dz.pharmaconnect.pharmaconnectpayment.client.config.MicroServiceClientConfig;
import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.delivery.DeliveryDto;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.api.requests.DeliveryCreationRequest;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.api.requests.DeliveryUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "PHARMA-CONNECT-DELIVERY", configuration = MicroServiceClientConfig.class)
public interface DeliveryClient {

    @GetMapping("/api/v1/deliveries")
    DeliveryDto getDelivery(@PathVariable Long orderId);


    @PostMapping("/api/v1/deliveries")
    DeliveryDto CreateDelivery(@RequestBody DeliveryCreationRequest request);


    @PutMapping("/api/v1/deliveries")
    DeliveryDto putDelivery(@RequestBody DeliveryUpdateRequest request);
}
