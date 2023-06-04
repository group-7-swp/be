package com.example.demo.controller;
import com.example.demo.Repository.DeliveryRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.model.Delivery;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {


    @GetMapping("/getDeliveryByDeliveryId")
    public ResponseEntity<Object> getDeliveryByDeliveryId (@RequestParam int deliveryId) throws Exception {
        Delivery delivery = DeliveryRepository.getDeliveryByDeliveryId(deliveryId);
        if (delivery.getDeliveryId() != 0) {
            return ResponseEntity.ok().body(delivery);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


}
