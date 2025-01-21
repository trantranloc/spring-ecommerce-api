package com.spring.springecommerceapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.springecommerceapi.dto.request.ApiRequest;
import com.spring.springecommerceapi.enums.OrderStatus;
import com.spring.springecommerceapi.exception.ErrorCode;
import com.spring.springecommerceapi.model.Cart;
import com.spring.springecommerceapi.model.Order;
import com.spring.springecommerceapi.repository.UserRepository;
import com.spring.springecommerceapi.service.CartService;
import com.spring.springecommerceapi.service.OrderService;


@RestController
@RequestMapping("/api/v1/orders")
public class OrderController extends BaseController {

    private final OrderService orderService;
    private final CartService cartService;

    public OrderController(UserRepository userRepository, OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<ApiRequest<List<Order>>> getAllOrders() {
        return createApiResponse(ErrorCode.SUCCESS, orderService.getAllOrders());
    }

    @PostMapping
    public ResponseEntity<ApiRequest<String>> createOrder(@RequestParam String userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        return createApiResponse(ErrorCode.SUCCESS, "null");
    }
}
