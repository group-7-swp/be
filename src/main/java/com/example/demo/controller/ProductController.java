package com.example.demo.controller;

import com.example.demo.model.Product;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/searchByName")
    public ResponseEntity<Object> searchByName(@RequestParam String productName) throws Exception {
        List<Product> productList = ProductRepository.searchByName(productName);
        return ResponseEntity.ok().body(productList);
    }

    @GetMapping("/getProductById")
    public ResponseEntity<Object> getProductById(@RequestParam int productId) throws Exception {
        List<Product> productList = ProductRepository.getProductById(productId);
        return ResponseEntity.ok().body(productList.get(0));
    }

    @GetMapping("/filterByCategory")
    public ResponseEntity<Object> filterByCategory(@Parameter String categoryId) throws Exception {
        List<Product> productList = ProductRepository.filterByCategory(categoryId);
        return ResponseEntity.ok().body(productList);
    }

    @GetMapping("/filterByPrice")
    public ResponseEntity<Object> filterByPrice(@Parameter int from, int to) throws Exception {
        List<Product> productList = ProductRepository.sortByPrice(from, to);
        return ResponseEntity.ok().body(productList);
    }

    @GetMapping("/filterByStatus")
    public ResponseEntity<Object> filterByStatus(@Parameter String status) throws Exception {
        List<Product> productList = ProductRepository.filterByStatus(status);
        return ResponseEntity.ok().body(productList);
    }

    @GetMapping("/multiFilter")
    public ResponseEntity<Object> multiFilter(@RequestParam(required = false) String categoryName, String price, String status) throws Exception {
        List<Product> productList = ProductRepository.multiFilter(categoryName, price, status);
        return ResponseEntity.ok().body(productList);
    }

    @PostMapping("/createProduct")
    public ResponseEntity<String> createProduct(@RequestBody Product product) throws Exception {
        return ProductRepository.createProduct(product);
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<String>  deleteProduct(@RequestParam int productId) throws Exception {
        return ProductRepository.deleteProduct(productId);
    }

    @PatchMapping("/updateProduct")
    public ResponseEntity<String>  updateProduct(@RequestBody Product product) throws Exception {
        return ProductRepository.updateProduct(product);
    }

    @GetMapping("/filterByCategory")
    public ResponseEntity<Object> getProductByCategoryId(@RequestParam int categoryId) throws Exception {
        List<Product> productList = ProductRepository.filterByCategoryId(categoryId);
        return ResponseEntity.ok().body(productList);
    }
}
