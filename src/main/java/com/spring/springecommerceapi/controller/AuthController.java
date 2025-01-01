package com.spring.springecommerceapi.controller;

import com.nimbusds.jose.JOSEException;
import com.spring.springecommerceapi.dto.request.ApiRequest;
import com.spring.springecommerceapi.dto.request.AuthRequest;
import com.spring.springecommerceapi.dto.request.IntrospectRequest;
import com.spring.springecommerceapi.dto.response.AuthResponse;
import com.spring.springecommerceapi.dto.response.IntrospectResponse;
import com.spring.springecommerceapi.exception.ErrorCode;
import com.spring.springecommerceapi.service.AuthService;
import com.spring.springecommerceapi.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.text.ParseException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController extends BaseController {

    private final AuthService authService;

    public AuthController(AuthService authService, UserService userService) {
        super();
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiRequest<AuthResponse>> login(@RequestBody AuthRequest authRequest) {
        try {
            var result = authService.authenticate(authRequest);
            return createApiResponse(ErrorCode.SUCCESS, result);
        } catch (Exception e) {
            return createApiResponse(ErrorCode.INVALID_EMAIL_OR_PASSWORD, null);
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Hủy session người dùng
        request.getSession().invalidate();
        SecurityContextHolder.clearContext();
        return "Logged out successfully";
    }

    @PostMapping("/introspect")
    public ResponseEntity<ApiRequest<IntrospectResponse>> introspect(@RequestBody IntrospectRequest intropectRequest)
            throws JOSEException, ParseException {
        var result = authService.introspect(intropectRequest);
        return createApiResponse(ErrorCode.SUCCESS, result);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiRequest<AuthResponse>> resetPassword(@RequestBody AuthRequest authRequest) {
        return createApiResponse(ErrorCode.SUCCESS, null);
    }
    
}
