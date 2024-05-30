package dz.pharmaconnect.pharmaconnectpayment.client;

import dz.pharmaconnect.pharmaconnectpayment.config.client.StockClientConfig;
import dz.pharmaconnect.pharmaconnectpayment.model.dto.client.stock.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PHARMA-CONNECT-STOCK-SERVICE", configuration = StockClientConfig.class)
public interface StockClient {


    @GetMapping("/api/v1/order/{orderId}")
    public Order getOrderById(@PathVariable Long orderId);


}
