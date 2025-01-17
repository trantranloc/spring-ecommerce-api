package com.spring.springecommerceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.springecommerceapi.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    
}
