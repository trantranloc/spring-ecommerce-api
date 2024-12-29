package com.spring.springecommerceapi.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.springecommerceapi.dto.request.OrderItemRequest;
import com.spring.springecommerceapi.dto.request.OrderRequest;
import com.spring.springecommerceapi.enums.OrderStatus;
import com.spring.springecommerceapi.model.Order;
import com.spring.springecommerceapi.model.OrderItem;
import com.spring.springecommerceapi.model.Product;
import com.spring.springecommerceapi.model.User;
import com.spring.springecommerceapi.repository.OrderRepository;
import com.spring.springecommerceapi.repository.ProductRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order createOrder(OrderRequest request, User user) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDDING);

        double totalPrice = 0.0;
        for (OrderItemRequest  itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            double itemPrice = product.getPrice() * itemRequest.getQuantity();
            totalPrice += itemPrice;

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(product.getPrice());

            order.getOrderItems().add(orderItem);
        }

        order.setTotalPrice(totalPrice);
        return orderRepository.save(order);
    }
}
