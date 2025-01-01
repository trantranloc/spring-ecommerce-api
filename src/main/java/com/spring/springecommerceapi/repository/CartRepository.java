package com.spring.springecommerceapi.repository;

import com.spring.springecommerceapi.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    Cart findByUserId(String userId);
}
