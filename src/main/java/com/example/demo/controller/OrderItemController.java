package com.example.demo.controller;

import com.example.demo.Repository.DeliveryRepository;
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
        if(orderList.size()>0) return ResponseEntity.ok().body(orderList);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getOrderItemById")
    public ResponseEntity<Object> getOrderItemById(int orderItemId) throws Exception {
        OrderItem order = OrderItemRepository.getOrderItemById(orderItemId);
        if(order != null) return ResponseEntity.ok().body(order);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getOrderItemByOrderId")
    public ResponseEntity<Object> getOrderItemByOrderId(int orderItemId) throws Exception {
        List<OrderItem> orderList = OrderItemRepository.getOrderItemByOrderId(orderItemId);
        if(orderList.size()>0) return ResponseEntity.ok().body(orderList);
        else return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/deleteOrderItem")
    public ResponseEntity<String> deleteOrderItem(@RequestParam int[] orderItemId) throws Exception {
        if(OrderItemRepository.deleteOrderItem(orderItemId)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/createOrderItem")
    public ResponseEntity<String> createOrderItem(@RequestBody OrderItem orderItem) throws Exception {
        if(OrderItemRepository.createOrderItem(orderItem)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/updateOrderItem")
    public ResponseEntity<String> updateOrderItem(@RequestBody OrderItem orderItem) throws Exception {
        if(OrderItemRepository.updateOrderItem(orderItem)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }
}
