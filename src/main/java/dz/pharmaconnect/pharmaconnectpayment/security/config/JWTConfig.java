package dz.pharmaconnect.pharmaconnectpayment.security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTConfig {
    @Value("${application.jwt.secret}")
    private String jwtSecret;


    @Bean
    public Algorithm getJWTAlgo() {
        return Algorithm.HMAC256(jwtSecret);
    }

    @Bean
    public JWTVerifier getJWTVerifier() {
        return JWT.require(this.getJWTAlgo()).build();
    }
}
