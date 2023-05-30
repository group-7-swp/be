package com.example.demo.controller;

import com.example.demo.Repository.CategoryRepository;
import com.example.demo.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @GetMapping("")
    public ResponseEntity<Object> getAllCategory() throws Exception {
        List<Category> categoryList = CategoryRepository.getAllCategory();
        return ResponseEntity.ok().body(categoryList);
    }
}
