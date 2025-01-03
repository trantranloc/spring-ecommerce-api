package com.spring.springecommerceapi.controller;

import com.spring.springecommerceapi.exception.AppException;
import com.spring.springecommerceapi.exception.ErrorCode;
import com.spring.springecommerceapi.model.Cart;
import com.spring.springecommerceapi.dto.request.ApiRequest;
import com.spring.springecommerceapi.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController extends BaseController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<ApiRequest<List<Cart>>> getAllCarts() {
        List<Cart> carts = cartService.getAllCarts();
        return createApiResponse(ErrorCode.SUCCESS, carts);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiRequest<Cart>> addProductToCart(@RequestParam String userId,
            @RequestParam String productId, @RequestParam(required = false, defaultValue = "1") int quantity) {
        try {
            Cart updatedCart = cartService.addProductToCart(userId, productId, quantity);
            return createApiResponse(ErrorCode.CREATE_SUCCESS, updatedCart);
        } catch (AppException e) {
            return createApiResponse(ErrorCode.PRODUCT_NOT_FOUND, null);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiRequest<Cart>> getCartByUserId(@PathVariable String id) {
        return createApiResponse(ErrorCode.SUCCESS, cartService.getCartByUserId(id));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<ApiRequest<Cart>> removeProductFromCart(
            @RequestParam String userId,
            @RequestParam String cartItemId) {
        try {
            Cart updatedCart = cartService.removeProductFromCart(userId, cartItemId);
            return createApiResponse(ErrorCode.DELETE_SUCCESS, updatedCart);
        } catch (AppException e) {
            return createApiResponse(e.getErrorCode(), null);
        } catch (Exception e) {
            e.printStackTrace();
            return createApiResponse(ErrorCode.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PutMapping("/increment")
    public ResponseEntity<ApiRequest<Cart>> incrementProductQuantity(
            @RequestParam String userId,
            @RequestParam String productId) {
        try {
            Cart updatedCart = cartService.incrementProductQuantity(userId, productId);
            return createApiResponse(ErrorCode.UPDATE_SUCCESS, updatedCart);
        } catch (AppException e) {
            return createApiResponse(e.getErrorCode(), null);
        }
    }

    @PutMapping("/decrement")
    public ResponseEntity<ApiRequest<Cart>> decrementProductQuantity(
            @RequestParam String userId,
            @RequestParam String productId) {
        try {
            Cart updatedCart = cartService.decrementProductQuantity(userId, productId);
            return createApiResponse(ErrorCode.UPDATE_SUCCESS, updatedCart);
        } catch (AppException e) {
            return createApiResponse(e.getErrorCode(), null);
        }
    }

}
