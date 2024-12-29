package com.spring.springecommerceapi.controller;

import com.spring.springecommerceapi.exception.ErrorCode;
import com.spring.springecommerceapi.model.User;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.spring.springecommerceapi.dto.request.ApiRequest;
import com.spring.springecommerceapi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiRequest<List<User>>> getAllUser() {
        var securityContextHolder = SecurityContextHolder.getContext().getAuthentication();
        log.info(securityContextHolder.getName());
        securityContextHolder.getAuthorities().forEach(
                grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        List<User> users = userService.getAllUsers();
        return createApiResponse(ErrorCode.SUCCESS, users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiRequest<User>> getUserById(@PathVariable("id") String id) {
        User user = userService.getUserById(id);
        return createApiResponse(ErrorCode.SUCCESS, user);
    }

    @PostMapping
    public ResponseEntity<ApiRequest<User>> createUser(@RequestBody User user) {
        User saveUser = userService.createUser(user);
        return createApiResponse(ErrorCode.SUCCESS, saveUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiRequest<String>> deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return createApiResponse(ErrorCode.DELETE_SUCCESS, null);
    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(401).body("User is not authenticated");
            }

            // Lấy token từ authentication
            Object principal = authentication.getPrincipal();
            String email = null;

            if (principal instanceof String) {
                String token = (String) principal; // Token JWT dưới dạng String
                SignedJWT signedJWT = SignedJWT.parse(token);
                JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
                email = claims.getSubject(); // Lấy email từ 'sub' (subject)
            }

            if (email == null) {
                return ResponseEntity.status(400).body("Email not found in token");
            }

            // Trả về thông tin người dùng (email trong ví dụ này)
            return ResponseEntity.ok("User email: " + email);

        } catch (Exception ex) {
            return ResponseEntity.status(500).body("An error occurred: " + ex.getMessage());
        }
    }
}