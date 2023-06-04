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
        if(orderList.size()>0) return ResponseEntity.ok().body(orderList);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getOrderById")
    public ResponseEntity<Object> getOrderById(int orderItemId) throws Exception {
        List<Order> orderList = OrderRepository.getOrderById(orderItemId);
        if(orderList.size()>0) return ResponseEntity.ok().body(orderList.get(0));
        else return ResponseEntity.badRequest().build();
    }
    @DeleteMapping("/deleteOrder")
    public ResponseEntity<String> deleteOrder(@RequestParam int[] orderItemId) throws Exception {
        return OrderRepository.deleteOrder(orderItemId);
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
        if(orderAndOrderItemList.size()>0) return ResponseEntity.ok().body(orderAndOrderItemList);
        else return ResponseEntity.badRequest().build();
    }
}
