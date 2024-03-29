// package dz.pharmaconnect.pharmaconnectpayment.security;

// import lombok.RequiredArgsConstructor;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import
// org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import
// org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.web.SecurityFilterChain;
// import
// org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// @Configuration
// @EnableWebSecurity
// @RequiredArgsConstructor
// public class SecurityConfiguration {

// @Bean
// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
// Exception {

// // http.csrf(csrf -> csrf
// // .disable())
// // .cors(cors -> cors
// // .disable())
// // .authorizeHttpRequests(auth -> auth
// // .requestMatchers("/api/v1/payment/**")
// // .permitAll()
// // .anyRequest()
// // .authenticated());
// // // .sessionManagement(session ->
// // // session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));
// return http.build();
// }
// }
