package com.example.demo.controller;

import com.example.demo.Repository.CategoryRepository;
import com.example.demo.model.Category;
import com.example.demo.model.CategoryAndProduct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @GetMapping("/getCategory")
    public ResponseEntity<Object> getAllCategory() throws Exception {
        List<Category> categoryList = CategoryRepository.getAllCategory();
        return ResponseEntity.ok().body(categoryList);
    }

    @GetMapping("/getCategoryAndProduct")
    public ResponseEntity<Object> getCategoryAndProduct() throws Exception {
        List<CategoryAndProduct> categoryAndProductList = CategoryRepository.getCategoryAndProduct();
        return ResponseEntity.ok().body(categoryAndProductList);
    }
}
