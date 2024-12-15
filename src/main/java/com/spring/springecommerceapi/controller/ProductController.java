package com.spring.springecommerceapi.controller;

import com.spring.springecommerceapi.exception.AppException;
import com.spring.springecommerceapi.exception.ErrorCode;
import com.spring.springecommerceapi.model.Category;
import com.spring.springecommerceapi.model.Product;
import com.spring.springecommerceapi.request.ApiRequest;
import com.spring.springecommerceapi.service.CategoryService;
import com.spring.springecommerceapi.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController extends BaseController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<ApiRequest<List<Product>>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return createApiResponse(ErrorCode.SUCCESS, products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiRequest<Product>> getProductById(@PathVariable String id) {
        Product product = productService.getProductById(id);
        return createApiResponse(ErrorCode.SUCCESS, product);
    }
    @PostMapping
    public ResponseEntity<ApiRequest<Product>> addProduct(@RequestBody Product product) {
        if (product.getCategoriesIds() == null || product.getCategoriesIds().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_CATEGORY);
        }
        Set<Category> categories = new HashSet<>();

        for (String categoryId : product.getCategoriesIds()) {
            Category category = categoryService.getCategoryById(categoryId);

            if (category == null) {
                throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
            }
            categories.add(category);
        }
        product.setCategories(categories);
        Product savedProduct = productService.saveProduct(product);
        return createApiResponse(ErrorCode.CREATE_SUCCESS, savedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiRequest<Void>> deleteProductById(@PathVariable String id) {
        productService.deleteProduct(id);
        return createApiResponse(ErrorCode.CREATE_SUCCESS, null);
    }
}
