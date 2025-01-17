package com.spring.springecommerceapi.service;

import com.spring.springecommerceapi.exception.AppException;
import com.spring.springecommerceapi.exception.ErrorCode;
import com.spring.springecommerceapi.model.Cart;
import com.spring.springecommerceapi.model.CartItem;
import com.spring.springecommerceapi.model.Product;
import com.spring.springecommerceapi.model.User;
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
    private final UserService userService;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, UserService userService,
            CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Cart addProductToCart(String userId, String productId, Integer quantity) {
        Cart cart = cartRepository.findByUserId(userId);
        User user = userService.getUserById(userId);
        if (cart == null) {
            Cart newCart = new Cart();
            newCart.setUser(user);
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

    @Transactional
    public Cart removeProductFromCart(String userId, String cartItemId) {
        // 1. Tìm cart của user
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new AppException(ErrorCode.CART_NOT_FOUND);
        }

        // 2. Tìm cartItem và verify ownership
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(ErrorCode.ITEM_NOT_FOUND));

        // 3. Kiểm tra xem cartItem có thuộc về cart của user không
        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // 4. Xóa cartItem khỏi danh sách items của cart
        cart.getItems().remove(cartItem);

        // 5. Xóa cartItem
        cartItemRepository.delete(cartItem);

        // 6. Lưu cart để cập nhật thay đổi
        return cartRepository.save(cart);
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