package com.example.demo.controller;

import com.example.demo.Repository.AddressRepository;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.model.Category;
import com.example.demo.model.CategoryAndProduct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Retention;
import java.security.PublicKey;
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

    @GetMapping("/searchById")
    public ResponseEntity<Object> searchById(@RequestParam int categoryId) throws Exception {
        Category category = CategoryRepository.getCategoryById(categoryId);
        if (category.getCategoryId() != 0) {
            return ResponseEntity.ok().body(category);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/createCategory")
    public ResponseEntity<String> createCategory(@RequestBody Category category) throws Exception {
        if (CategoryRepository.createCategory(category)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }
    @PatchMapping("/updateCategory")
    public ResponseEntity<String> updateCategory(@RequestBody Category category) throws Exception {
        if (CategoryRepository.updateCategory(category)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }
    @DeleteMapping("/deleteCategory")
    public ResponseEntity<String> deleteCategory(@RequestParam int[] categoryId) throws Exception {
        if (CategoryRepository.deleteCategory(categoryId)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getCategoryAndProduct")
    public ResponseEntity<Object> getCategoryAndProduct() throws Exception {
        List<CategoryAndProduct> categoryAndProductList = CategoryRepository.getCategoryAndProduct();
        return ResponseEntity.ok().body(categoryAndProductList);
    }
}
