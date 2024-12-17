package com.spring.springecommerceapi.controller;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;
import com.spring.springecommerceapi.dto.request.ApiRequest;
import com.spring.springecommerceapi.dto.request.AuthRequest;
import com.spring.springecommerceapi.dto.request.IntrospectRequest;
import com.spring.springecommerceapi.dto.response.AuthResponse;
import com.spring.springecommerceapi.dto.response.IntrospectResponse;
import com.spring.springecommerceapi.exception.ErrorCode;
import com.spring.springecommerceapi.service.AuthService;

import java.text.ParseException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController extends BaseController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        super();
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiRequest<AuthResponse>> login(@RequestBody AuthRequest authRequest) throws KeyLengthException {
        var result =  authService.authenticate(authRequest);
            return createApiResponse(ErrorCode.SUCCESS, result);
    }
    @PostMapping("/introspect")
    public ResponseEntity<ApiRequest<IntrospectResponse>> introspect(@RequestBody IntrospectRequest intropectRequest) throws JOSEException,ParseException {
        var result =  authService.introspect(intropectRequest);
            return createApiResponse(ErrorCode.SUCCESS, result);
    }
}
