package com.spring.springecommerceapi.repository;

import com.spring.springecommerceapi.model.Cart;
import com.spring.springecommerceapi.model.CartItem;
import com.spring.springecommerceapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {    
    CartItem findByProductId(Product product);
    CartItem findByCartAndProduct(Cart cart, Product product);
}
