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
        if(productList.size()>0) return ResponseEntity.ok().body(productList);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/searchByName")
    public ResponseEntity<Object> searchByName(@RequestParam String productName) throws Exception {
        List<Product> productList = ProductRepository.searchByName(productName);
        if(productList.size()>0) return ResponseEntity.ok().body(productList);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getProductById")
    public ResponseEntity<Object> getProductById(@RequestParam int productId) throws Exception {
        List<Product> productList = ProductRepository.getProductById(productId);
        if(productList.size()>0) return ResponseEntity.ok().body(productList.get(0));
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/filterByPrice")
    public ResponseEntity<Object> filterByPrice(@Parameter int from, int to) throws Exception {
        List<Product> productList = ProductRepository.sortByPrice(from, to);
        if(productList.size()>0) return ResponseEntity.ok().body(productList);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/filterByStatus")
    public ResponseEntity<Object> filterByStatus(@Parameter String status) throws Exception {
        List<Product> productList = ProductRepository.filterByStatus(status);
        if(productList.size()>0) return ResponseEntity.ok().body(productList);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/multiFilter")
    public ResponseEntity<Object> multiFilter(@RequestParam(required = false) String categoryName, String price, String status) throws Exception {
        List<Product> productList = ProductRepository.multiFilter(categoryName, price, status);
        if(productList.size()>0) return ResponseEntity.ok().body(productList);
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/createProduct")
    public ResponseEntity<String> createProduct(@RequestBody Product product) throws Exception {
        return ProductRepository.createProduct(product);
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<String>  deleteProduct(@RequestParam int[] productId) throws Exception {
        return ProductRepository.deleteProduct(productId);
    }

    @PatchMapping("/updateProduct")
    public ResponseEntity<String>  updateProduct(@RequestBody Product product) throws Exception {
        return ProductRepository.updateProduct(product);
    }

    @GetMapping("/filterByCategory")
    public ResponseEntity<Object> getProductByCategoryId(@RequestParam int categoryId) throws Exception {
        List<Product> productList = ProductRepository.filterByCategoryId(categoryId);
        if(productList.size()>0) return ResponseEntity.ok().body(productList);
        else return ResponseEntity.badRequest().build();
    }
}
