package com.spring.springecommerceapi.controller;

import com.spring.springecommerceapi.model.Category;
import com.spring.springecommerceapi.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") String id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }
    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        categoryService.saveCategory(category);
        return ResponseEntity.ok(category);
    }
}
