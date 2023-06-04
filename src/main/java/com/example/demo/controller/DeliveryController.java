package com.example.demo.controller;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.DeliveryRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.model.Cart;
import com.example.demo.model.Delivery;
import com.example.demo.model.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
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

    @PostMapping("/createDelivery")
    public ResponseEntity<String> createDelivery(@RequestParam Delivery delivery) throws Exception {
        return DeliveryRepository.createDelivery(delivery);
    }

    @PatchMapping("/updateDelivery")
    public ResponseEntity<String> updateDelivery(@RequestParam Delivery delivery) throws Exception {
        return DeliveryRepository.updateDelivery(delivery);
    }

    @DeleteMapping("/deleteDeliery")
    public ResponseEntity<String> deleteDelivery(@RequestParam int[] deliveryId) throws Exception {
        return DeliveryRepository.deleteDelivery(deliveryId);
    }
}
