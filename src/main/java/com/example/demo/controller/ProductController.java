package com.example.demo.controller;

import com.example.demo.model.Product;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Repository.ProductRepository;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    //Get all product
    @GetMapping("/allProduct")
    public ResponseEntity<Object> getAllProduct() throws Exception {
        List<Product> productList = ProductRepository.getAllProduct();
        return ResponseEntity.ok().body(productList);
    }

    //Search product by name
    @GetMapping("/searchByName")
    public ResponseEntity<Object> searchByName(@Parameter String productName) throws Exception {
        List<Product> productList = ProductRepository.searchByName(productName);
        return ResponseEntity.ok().body(productList);
    }

    @GetMapping("/filterByCategory")
    public ResponseEntity<Object> filterByCategory(@Parameter String categoryId) throws Exception {
        List<Product> productList = ProductRepository.filterByCategory(categoryId);
        return ResponseEntity.ok().body(productList);
    }

    @GetMapping("/sortByPrice")
    public ResponseEntity<Object> sortByPriceAsc(@Parameter String order) throws Exception {
        List<Product> productList = ProductRepository.sortByPrice(order);
        return ResponseEntity.ok().body(productList);
    }

    @GetMapping("/filterByStatus")
    public ResponseEntity<Object> filterByStatus(@Parameter String status) throws Exception {
        List<Product> productList = ProductRepository.filterByStatus(status);
        return ResponseEntity.ok().body(productList);
    }

    @GetMapping("/updateQuantity")
    public ResponseEntity<Object> updateQuantity(@Parameter int buyQuantity, int productId) throws Exception {
        String update = ProductRepository.updateQuantity(buyQuantity, productId);
        return ResponseEntity.ok().body(update);
    }
}
