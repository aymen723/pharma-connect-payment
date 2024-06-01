package dz.pharmaconnect.pharmaconnectpayment.client;

import dz.pharmaconnect.pharmaconnectpayment.client.config.MicroServiceClientConfig;
import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.stock.Order;
import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.stock.Pharmacy;
import dz.pharmaconnect.pharmaconnectpayment.model.schema.api.requests.OrderCreationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PHARMA-CONNECT-STOCK-SERVICE", configuration = MicroServiceClientConfig.class)
public interface StockClient {


    @GetMapping("/api/v1/orders/{orderId}")
    Order getOrderById(@PathVariable Long orderId);

    @GetMapping("/api/v1/orders/{orderId}/pharmacy")
    Pharmacy getPharmacyByOrderId(@PathVariable Long orderId);

    @PostMapping("/api/v1/orders")
    Order CreateOrder(@RequestBody OrderCreationRequest request);


}
