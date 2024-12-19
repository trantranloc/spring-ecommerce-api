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
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

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
        var token = generateToken(user);
        return new AuthResponse.Builder()
                .token(token)
                .isAuthenticated(true)
                .build();
    }
        private String generateToken(User user) throws KeyLengthException {
            JWSHeader jwsHeader =new JWSHeader(JWSAlgorithm.HS512);
            JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .claim("scope", buildScope(user))
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
    private String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles().forEach(role -> stringJoiner.add(role.getName()));
        }
        return stringJoiner.toString();
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
