package com.example.demo.controller;

import com.example.demo.Repository.CartItemsRepository;
import com.example.demo.model.CartItems;
import com.example.demo.model.ProductAndCartItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cartItem")
public class CartItemController {
    @GetMapping("/allCartItems")
    public ResponseEntity<Object> allCartItem() throws Exception {
        List<CartItems> cartList = CartItemsRepository.getAllCartItems();
        if(cartList.size()>0) return ResponseEntity.ok().body(cartList);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getCartItemsById")
    public ResponseEntity<Object> getCartItemsById(@RequestParam int cartItemId) throws Exception {
        List<CartItems> cartList = CartItemsRepository.getCartItemsById(cartItemId);
        if(cartList.size()>0) return ResponseEntity.ok().body(cartList.get(0));
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/createCartItems")
    public ResponseEntity<String> createCartItems(@RequestBody CartItems cartItems) throws Exception {
        return CartItemsRepository.createCartItems(cartItems);
    }

    @PostMapping("/updateCartItems")
    public ResponseEntity<String> updateCartItems(@RequestBody CartItems cartItems) throws Exception {
        return CartItemsRepository.updateCartItems(cartItems);
    }

    @DeleteMapping("/deleteCartItems")
    public ResponseEntity<String> deleteCartItem(@RequestParam int[] cartItemId) throws Exception {
        return CartItemsRepository.deleteCartItem(cartItemId);
    }

    @GetMapping("/countCartItems")
    public ResponseEntity<Integer> countCartItems(@RequestParam int cartId) throws Exception {
        int count = CartItemsRepository.countCartItems(cartId);
        if(count>0) return ResponseEntity.ok().body(count);
        else return ResponseEntity.badRequest().build();
    }
}
