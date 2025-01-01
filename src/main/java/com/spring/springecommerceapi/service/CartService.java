package com.spring.springecommerceapi.service;

import com.spring.springecommerceapi.exception.AppException;
import com.spring.springecommerceapi.exception.ErrorCode;
import com.spring.springecommerceapi.model.Cart;
import com.spring.springecommerceapi.model.CartItem;
import com.spring.springecommerceapi.model.Product;
import com.spring.springecommerceapi.repository.CartItemRepository;
import com.spring.springecommerceapi.repository.CartRepository;
import com.spring.springecommerceapi.repository.ProductRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository,
            CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Cart addProductToCart(String userId, String productId, Integer quantity) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            Cart newCart = new Cart();
            newCart.setUser(userId);
            cart = cartRepository.save(newCart);
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        CartItem existingCart = cartItemRepository.findByCartAndProduct(cart, product);

        if (existingCart != null) {
            existingCart.setQuantity(existingCart.getQuantity() + quantity);
            cartItemRepository.save(existingCart);
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            cartItemRepository.save(newCartItem);
        }
        return cart;
    }

    public Cart removeProductFromCart(String userId, String cartItemId) {
        // Lấy giỏ hàng của người dùng
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new AppException(ErrorCode.CART_NOT_FOUND);
        }
        // Xóa cartItem
        cartItemRepository.deleteById(cartItemId);

        return cart;
    }
    @Transactional
    public Cart incrementProductQuantity(String userId, String productId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new AppException(ErrorCode.CART_NOT_FOUND);
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product);
        if (cartItem == null) {
            throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        }

        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cartItemRepository.save(cartItem);
        return cart;
    }

    @Transactional
    public Cart decrementProductQuantity(String userId, String productId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new AppException(ErrorCode.CART_NOT_FOUND);
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product);
        if (cartItem == null) {
            throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        }

        if (cartItem.getQuantity() <= 1) {
            // If quantity would become 0, remove the item entirely
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            cartItemRepository.save(cartItem);
        }

        return cart;
    }

    public Cart getCartByUserId(String id) {
        return cartRepository.findByUserId(id);
    }
}