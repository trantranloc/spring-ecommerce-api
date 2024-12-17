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
    public ResponseEntity<ApiRequest<Cart>> addProductToCart(@RequestParam String userId, @RequestParam String productId , @RequestParam int quantity) {
        try {
            Cart updatedCart = cartService.addProductToCart(userId, productId, quantity);
            return createApiResponse(ErrorCode.CREATE_SUCCESS,updatedCart);
        }catch (AppException e) {
            return createApiResponse(ErrorCode.PRODUCT_NOT_FOUND,null);
        }
    }
}
