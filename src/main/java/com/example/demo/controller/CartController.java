package com.example.demo.controller;

<<<<<<< Updated upstream
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
=======
import com.example.demo.Repository.AddressRepository;
import com.example.demo.Repository.CartRepository;
import com.example.demo.model.Address;
import com.example.demo.model.Cart;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
>>>>>>> Stashed changes

@RestController
@RequestMapping("/api/cart")
public class CartController {
<<<<<<< Updated upstream
=======
    @GetMapping("/allCart")
    public ResponseEntity<Object> getAllCart() throws Exception {
        List<Cart> cartList = CartRepository.getAllCart();
        if(cartList.size()>0) return ResponseEntity.ok().body(cartList);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getCartById")
    public ResponseEntity<Object> getCartByCartId(@RequestParam int cartId) throws Exception {
        List<Cart> cartList = CartRepository.getCartByCartId(cartId);
        if(cartList.size()>0) return ResponseEntity.ok().body(cartList.get(0));
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/createCart")
    public ResponseEntity<String> createCart(@RequestBody Cart cart) throws Exception {
        return CartRepository.createCart(cart);
    }

    @DeleteMapping("/deleteCart")
    public ResponseEntity<String> deleteCart(@RequestParam int[] cartId) throws Exception {
        return CartRepository.deleteCart(cartId);
    }
>>>>>>> Stashed changes
}
