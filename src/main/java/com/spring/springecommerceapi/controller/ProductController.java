package com.spring.springecommerceapi.controller;

import com.spring.springecommerceapi.exception.AppException;
import com.spring.springecommerceapi.exception.ErrorCode;
import com.spring.springecommerceapi.model.Category;
import com.spring.springecommerceapi.model.Product;
import com.spring.springecommerceapi.dto.request.ApiRequest;
import com.spring.springecommerceapi.service.CategoryService;
import com.spring.springecommerceapi.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<ApiRequest<List<Product>>> addProducts(@RequestBody List<Product> products) {
        if (products == null || products.isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_ALREADY_DELETED);
        }

        List<Product> savedProducts = new ArrayList<>();

        for (Product product : products) {
            // Kiểm tra nếu sản phẩm không có danh mục
            if (product.getCategories() == null || product.getCategories().isEmpty()) {
                throw new AppException(ErrorCode.INVALID_CATEGORY);
            }

            Set<Category> categories = new HashSet<>();

            // Duyệt qua từng categoryId và thêm vào danh sách categories
//            for (String categoryId : product.getCategories()) {
//                Category category = categoryService.getCategoryById(categoryId);
//
//                if (category == null) {
//                    // Tạo mới danh mục nếu không tìm thấy
//                    category = new Category();
//                    category.setId(categoryId); // Gán ID mới
//                    categoryService.saveCategory(category); // Lưu vào cơ sở dữ liệu
//                }
//
//                categories.add(category);
//            }

            // Gán danh mục vào sản phẩm
            product.setCategories(categories);

            // Lưu sản phẩm vào cơ sở dữ liệu
            Product savedProduct = productService.saveProduct(product);
            savedProducts.add(savedProduct);
        }

        return createApiResponse(ErrorCode.CREATE_SUCCESS, savedProducts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiRequest<Void>> deleteProductById(@PathVariable String id) {
        productService.deleteProduct(id);
        return createApiResponse(ErrorCode.CREATE_SUCCESS, null);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiRequest<Void>> updateProduct(@PathVariable String id, @RequestBody Product product) {
        Product productUpdate = productService.getProductById(id);
        if (product.getCategories() == null || product.getCategories().isEmpty()) {
            product.setCategories(productUpdate.getCategories());
        }
        if (product.getImages() != null) {
            productUpdate.setImages(product.getImages());
        }
        if (product.getName() != null) {
            productUpdate.setName(product.getName());
        }
        if (product.getDescription() != null) {
            productUpdate.setDescription(product.getDescription());
        }
        if (product.getPrice() != null) {
            productUpdate.setPrice(product.getPrice());
        }
        if (product.getSku() != null) {
            productUpdate.setSku(product.getSku());
        }
        productService.updateProduct(productUpdate);
        return createApiResponse(ErrorCode.UPDATE_SUCCESS, null);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiRequest<List<Product>>> getProductsByTitle(@RequestParam String title) {
        List<Product> products = productService.getProductByTitle(title);
        return createApiResponse(ErrorCode.SUCCESS, products);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filteredProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        // Giả định gọi service để lọc sản phẩm
        List<Product> filteredProducts = productService.filteredProducts(name, category, minPrice, maxPrice);
        return ResponseEntity.ok(filteredProducts);
    }

}
