package com.spring.springecommerceapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.springecommerceapi.dto.request.OrderRequest;
import com.spring.springecommerceapi.model.Order;
import com.spring.springecommerceapi.model.User;
import com.spring.springecommerceapi.repository.UserRepository;
import com.spring.springecommerceapi.service.OrderService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final UserRepository userRepository;
    private final OrderService orderService;

    public OrderController(UserRepository userRepository, OrderService orderService) {
        this.userRepository = userRepository;
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra nếu người dùng chưa đăng nhập hoặc không hợp lệ
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("User is not authenticated");
        }

        // Lấy email từ Authentication
        String email = null;
        if (authentication.getPrincipal() instanceof UserDetails) {
            email = ((UserDetails) authentication.getPrincipal()).getUsername(); // Username là email
        }

        // Tìm người dùng qua email
        User currentUser = userRepository.findByEmail(email).orElse(null);
        if (currentUser == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        // Tạo đơn hàng
        Order order = orderService.createOrder(request, currentUser);
        return ResponseEntity.ok(order);
    }
}
