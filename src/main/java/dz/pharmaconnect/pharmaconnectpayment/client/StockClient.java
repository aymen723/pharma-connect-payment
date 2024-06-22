package dz.pharmaconnect.pharmaconnectpayment.client;

import dz.pharmaconnect.pharmaconnectpayment.client.config.MicroServiceClientConfig;
import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.stock.OrderDto;
import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.stock.PharmacyDto;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.api.requests.OrderCreationRequest;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.api.requests.OrderUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "PHARMA-CONNECT-STOCK-SERVICE", configuration = MicroServiceClientConfig.class)
public interface StockClient {


    @GetMapping("/api/v1/orders/{orderId}")
    OrderDto getOrderById(@PathVariable Long orderId);

    @GetMapping("/api/v1/orders/{orderId}/pharmacy")
    PharmacyDto getPharmacyByOrderId(@PathVariable Long orderId);

    @PostMapping("/api/v1/orders")
    OrderDto CreateOrder(@RequestBody OrderCreationRequest request);

    @PutMapping("/api/v1/orders")
    OrderDto patchOrder(@RequestBody OrderUpdateRequest order);


}
