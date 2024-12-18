package com.spring.springecommerceapi.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.spring.springecommerceapi.dto.request.AuthRequest;
import com.spring.springecommerceapi.dto.request.IntrospectRequest;
import com.spring.springecommerceapi.dto.response.AuthResponse;
import com.spring.springecommerceapi.dto.response.IntrospectResponse;
import com.spring.springecommerceapi.exception.AppException;
import com.spring.springecommerceapi.exception.ErrorCode;
import com.spring.springecommerceapi.model.User;
import com.spring.springecommerceapi.repository.UserRepository;
import lombok.experimental.NonFinal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class AuthService {
    private final UserRepository userRepository;
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @NonFinal
    public   static final String SIGNER_KEY="n47X3QZLGE+YF2BNbxdNnTwLk5y5G/ByOvPgOL6l59MoRsY2gqYau5ItAfpS2vJk";

    public AuthResponse authenticate(AuthRequest response) throws KeyLengthException {
        User user = userRepository.findByUsername(response.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        var authenticated = passwordEncoder.matches(response.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateToken(response.getUsername());
        return new AuthResponse.Builder()
                .token(token)
                .isAuthenticated(true)
                .build();
    }
        private String generateToken(String username) throws KeyLengthException {
            JWSHeader jwsHeader =new JWSHeader(JWSAlgorithm.HS512);
            JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                    .subject(username)
                    .claim("authorities", "ROLE_USER")
                    .issueTime(new Date())
                    .expirationTime(new Date(System.currentTimeMillis() + 3600000))

                    .build();

            Payload payload = new Payload(jwtClaimsSet.toJSONObject());

            JWSObject jwsObject = new JWSObject(jwsHeader,payload);
            try {
                jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
                return jwsObject.serialize();
            } catch (JOSEException e) {
                throw new RuntimeException(e);
            }
            
    }

    public IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException,ParseException {
        var token = introspectRequest.getToken();
        JWSVerifier jwsVerifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        System.out.println("Expiry Time: " + expiryTime);
        System.out.println("Current Time: " + new Date());

        var verified =  signedJWT.verify(jwsVerifier);
        return new IntrospectResponse.Builder()
                .valid(verified && expiryTime.after(new Date())) 
                .build();
    }

} 
