package dz.pharmaconnect.pharmaconnectpayment.security.services;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JWTService {

    private final JWTVerifier jwtVerifier;


    public Optional<String> extractAccessToken(HttpServletRequest request) {
        String authorization = request.getHeader("authorization");

        if (authorization != null && authorization.startsWith("Bearer ")) {
            return Optional.of(authorization.substring(7));
        }
        return Optional.empty();
    }


    public Optional<DecodedJWT> verifyToken(String token) {
        try {

            return Optional.of(jwtVerifier.verify(token));
        } catch (Exception e) {
            return Optional.empty();
        }


    }

}
