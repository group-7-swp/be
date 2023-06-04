package com.example.demo.controller;

import com.example.demo.Repository.OrderItemRepository;
import com.example.demo.model.OrderItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderItem")
public class OrderItemController {
    @GetMapping("/getAllOrderItem")
    public ResponseEntity<Object> getAllOrderItem() throws Exception {
        List<OrderItem> orderList = OrderItemRepository.getAllOrderItem();
        return ResponseEntity.ok().body(orderList);
    }

    @GetMapping("/getOrderItemById")
    public ResponseEntity<Object> getOrderItemById(int orderId) throws Exception {
        List<OrderItem> orderList = OrderItemRepository.getOrderItemById(orderId);
        return ResponseEntity.ok().body(orderList);
    }
    @DeleteMapping("/deleteOrderItem")
    public ResponseEntity<String> deleteOrderItem(@RequestParam int orderId, @RequestParam int productId) throws Exception {
        return OrderItemRepository.deleteOrderItem(orderId, productId);
    }

    @PostMapping("/createOrderItem")
    public ResponseEntity<String> createOrderItem(@RequestBody OrderItem orderItem) throws Exception {
        return OrderItemRepository.createOrderItem(orderItem);
    }

    @PostMapping("/updateOrderItem")
    public ResponseEntity<String> updateOrderItem(@RequestBody OrderItem orderItem) throws Exception {
        return OrderItemRepository.updateOrderItem(orderItem);
    }
}
