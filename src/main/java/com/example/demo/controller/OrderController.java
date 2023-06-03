package com.example.demo.controller;

import com.example.demo.Repository.OrderRepository;
import com.example.demo.model.Order;
import com.example.demo.model.OrderAndOrderItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @GetMapping("/getAllOrder")
    public ResponseEntity<Object> getAllOrder() throws Exception {
        List<Order> orderList = OrderRepository.getAllOrder();
        return ResponseEntity.ok().body(orderList);
    }

    @GetMapping("/getOrderById")
    public ResponseEntity<Object> getOrderById(int orderId) throws Exception {
        List<Order> orderList = OrderRepository.getOrderById(orderId);
        return ResponseEntity.ok().body(orderList);
    }
    @DeleteMapping("/deleteOrder")
    public ResponseEntity<String> deleteOrder(@RequestParam int orderId) throws Exception {
        return OrderRepository.deleteOrder(orderId);
    }

    @PostMapping("/createOrder")
    public ResponseEntity<String> createOrder(@RequestBody Order order) throws Exception {
        return OrderRepository.createOrder(order);
    }

    @PostMapping("/updateOrder")
    public ResponseEntity<String> updateOrder(@RequestBody Order order) throws Exception {
        return OrderRepository.updateOrder(order);
    }

    @PostMapping("/getOrderAndOrderItem")
    public ResponseEntity<Object> getOrderAndOrderItem() throws Exception {
        List<OrderAndOrderItem> orderAndOrderItemList = OrderRepository.getOrderAndOrderItem();
        return ResponseEntity.ok().body(orderAndOrderItemList);
    }
}
