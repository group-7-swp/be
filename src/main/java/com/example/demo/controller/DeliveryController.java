package com.example.demo.controller;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.DeliveryRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.model.Cart;
import com.example.demo.model.Delivery;
import com.example.demo.model.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {

    @GetMapping("/getDeliveryById")
    public ResponseEntity<Object> getDeliveryByDeliveryId (@RequestParam int deliveryId) throws Exception {
        Delivery delivery = DeliveryRepository.getDeliveryById(deliveryId);
        if (delivery.getDeliveryId() != 0) return ResponseEntity.ok().body(delivery);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getAllDelivery")
    public ResponseEntity<Object> getAllDelivery() throws Exception {
        List<Delivery> deliveryList = DeliveryRepository.getAllDelivery();
        if(deliveryList.size()>0) return ResponseEntity.ok().body(deliveryList);
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
}
