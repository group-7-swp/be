package com.example.demo.controller;

import com.example.demo.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @GetMapping("")
    public ResponseEntity<Object> getAllCategory() {
        int categoryId = 5;
        String categoryName= "Đi chơi";
        Category category = new Category(categoryId, categoryName);
        return ResponseEntity.ok().body(category);
    }
}
