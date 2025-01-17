package com.spring.springecommerceapi.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.springecommerceapi.model.Order;
import com.spring.springecommerceapi.repository.OrderRepository;


@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}