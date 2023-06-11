package com.example.demo.controller;

import com.example.demo.Repository.CartItemsRepository;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.model.Cart;
import com.example.demo.model.CartAndCartItem;
import com.example.demo.model.User;
import com.example.demo.model.CartAndCartItemAndProduct;
import com.example.demo.model.ProductAndCartItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
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

    @GetMapping("/getCartByUserId")
    public ResponseEntity<Object> getCartByUserId(@RequestParam int userId) throws Exception {
        List<Cart> cartList = CartRepository.getCartByUserId(userId);
        if(cartList.size()>0) return ResponseEntity.ok().body(cartList.get(0));
        else return ResponseEntity.badRequest().build();
    }
    @GetMapping("/getCartByUserUid")
    public ResponseEntity<Object> getCartByUserUid(@RequestParam String userUid) throws Exception {
        Cart cart = CartRepository.getCartByUserUid(userUid);
        if (cart.getCartId() != 0) {
            return ResponseEntity.ok().body(cart);
        } else {
            return ResponseEntity.badRequest().build();
        }
    } //Chưa chắc lắm nên tạm để đây

    @PostMapping("/createCart")
    public ResponseEntity<String> createCart(@RequestBody Cart cart) throws Exception {
        return CartRepository.createCart(cart);
    }

    @DeleteMapping("/deleteCart")
    public ResponseEntity<String> deleteCart(@RequestParam int[] cartId) throws Exception {
        return CartRepository.deleteCart(cartId);
    }

    @GetMapping("/getCartAndCartItem")
    public ResponseEntity<Object> getCartAndCartItem() throws Exception {
        List<CartAndCartItem> cartAndCartItem = CartRepository.getCartAndCartItem();
        if(cartAndCartItem.size()>0) return ResponseEntity.ok().body(cartAndCartItem);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getCartProductByUserUid")
    public ResponseEntity<Object> getCartProductByUserUid(@RequestParam String userUid) throws Exception {
        CartAndCartItemAndProduct cartAndCartItemAndProduct = CartRepository.getCartProductByUserUid(userUid);
        if(cartAndCartItemAndProduct != null) return ResponseEntity.ok().body(cartAndCartItemAndProduct);
        else return ResponseEntity.badRequest().build();
    }
}
