package dz.pharmaconnect.pharmaconnectpayment.client.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class MicroServiceClientConfig {

    @Value("${application.jwt.server.token}")
    private String token;

    @Bean
    public RequestInterceptor getInterceptor() {
        return requestTemplate -> {
            // Retrieve the JWT token from a secure source (e.g., an authentication service)

            requestTemplate.header("Authorization", "Bearer " + token);
        };
    }
}
