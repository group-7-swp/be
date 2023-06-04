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

    @GetMapping("/allCategory")

    public ResponseEntity<Object> getAllCategory() throws Exception {
        List<Category> categoryList = CategoryRepository.getAllCategory();
        if (categoryList.size() != 0) {
            return ResponseEntity.ok().body(categoryList);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    //
    @GetMapping("/searchById")
    public ResponseEntity<Object> searchById(@RequestParam int categoryId) throws Exception {
        Category category = CategoryRepository.getCategoryById(categoryId);
        if (category.getCategoryId() != 0) {
            return ResponseEntity.ok().body(category);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    //
    @GetMapping("/getCategoryAndProduct")
    public ResponseEntity<Object> getCategoryAndProduct() throws Exception {
        List<CategoryAndProduct> categoryAndProductList = CategoryRepository.getCategoryAndProduct();
        return ResponseEntity.ok().body(categoryAndProductList);
    }
}
