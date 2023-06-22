package com.example.demo.controller;

import com.example.demo.DBConnection.DBUtils;
import com.example.demo.Repository.*;
import com.example.demo.model.*;
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
        Order order = OrderRepository.getOrderById(orderItemId);
        if(order != null) return ResponseEntity.ok().body(order);
        else return ResponseEntity.badRequest().build();
    }
    @DeleteMapping("/deleteOrder")
    public ResponseEntity<Object> deleteOrder(@RequestParam int[] orderItemId) throws Exception {
        if(OrderRepository.deleteOrder(orderItemId)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/createOrder")
    public ResponseEntity<Object> createOrder(@RequestBody Order order) throws Exception {
        if(OrderRepository.createOrder(order)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/updateOrder")
    public ResponseEntity<Object> updateOrder(@RequestBody Order order) throws Exception {
        if(OrderRepository.updateOrder(order)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/makeOrder")
    public ResponseEntity<Object> makeOrder(@RequestBody OrderDetails orderDetails) throws Exception {
        if(OrderDetailsRepository.createOrderDetails(orderDetails)) return ResponseEntity.ok().body("Thành công");
        else return ResponseEntity.badRequest().body("Đã xa ra lỗi! Vui lòng kiểm tra lại giỏ hàng hoặc thông tin giao hàng.");
    }

    @GetMapping("/getOrderAndOrderItem")
    public ResponseEntity<Object> getOrderAndOrderItem() throws Exception {
        List<OrderAndOrderItem> orderAndOrderItemList = OrderDetailsRepository.getAllOrderDetails();
        if(orderAndOrderItemList.size()>0) return ResponseEntity.ok().body(orderAndOrderItemList);
        else return ResponseEntity.badRequest().build();
    }
}
