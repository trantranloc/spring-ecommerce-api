package com.spring.springecommerceapi.service;

import com.spring.springecommerceapi.exception.AppException;
import com.spring.springecommerceapi.exception.ErrorCode;
import com.spring.springecommerceapi.model.Cart;
import com.spring.springecommerceapi.model.CartItem;
import com.spring.springecommerceapi.model.Product;
import com.spring.springecommerceapi.repository.CartItemRepository;
import com.spring.springecommerceapi.repository.CartRepository;
import com.spring.springecommerceapi.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public List<Cart> getAllCarts(){
        return cartRepository.findAll();
    }

    public Cart addProductToCart(String userId, String productId, int quantity) {

        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            cart = cartRepository.save(newCart);
        }
        Product product = productRepository.findById(productId).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        CartItem existingCart = cartItemRepository.findByCartIdAndProductId(cart ,product);
        if (existingCart != null) {
            existingCart.setQuantity(existingCart.getQuantity() + quantity);
            cartItemRepository.save(existingCart);
        }else{
            CartItem newCartItem = new CartItem();
            newCartItem.setCartId(cart);
            newCartItem.setProductId(product);
            newCartItem.setQuantity(quantity);
            cartItemRepository.save(newCartItem);
        }
        return cart;
    }

    public void removeCart(Cart cart) {
        cartRepository.delete(cart);
    }

    public Cart getCart(Cart cart) {
        return cartRepository.getReferenceById(cart.getId());
    }
}
