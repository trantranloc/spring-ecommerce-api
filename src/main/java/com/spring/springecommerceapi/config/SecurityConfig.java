package com.spring.springecommerceapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    protected static final String SIGNER_KEY = "n47X3QZLGE+YF2BNbxdNnTwLk5y5G/ByOvPgOL6l59MoRsY2gqYau5ItAfpS2vJk";
    protected String[] PUBLIC = {"/api/v1/auth/**"};

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        registry -> registry
                                .requestMatchers(HttpMethod.POST, PUBLIC).permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(
                        auth2 -> auth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()))
                )
                .formLogin(
                        form -> form
                                .loginPage("/login").permitAll()
                                .successHandler((request, response, authentication) -> response.sendRedirect("/api/v1"))
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    JwtDecoder jwtDecoder() {
        byte[] keyBytes = SIGNER_KEY.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "HmacSHA512");

        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }
}
