package com.example.demo.controller;

import com.example.demo.Repository.AddressRepository;
import com.example.demo.Repository.PaymentRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.model.Address;
import com.example.demo.model.Payment;
import com.example.demo.model.User;
import net.bytebuddy.asm.Advice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @GetMapping("/allPayment")
    public ResponseEntity<Object> getAllPayment() throws Exception {
        List<Payment> paymentList = PaymentRepository.getAllPayment();
        if (paymentList.size() != 0) {
            return ResponseEntity.ok().body(paymentList);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getPaymentById")
    public ResponseEntity<Object> getPaymentById(@RequestParam int paymentId) throws Exception {
        List<Payment> paymentList = PaymentRepository.getAllPayment();
        if (paymentList.size() != 0) return ResponseEntity.ok().body(paymentList.get(0));
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/createPayment")
    public ResponseEntity<Object> createPayment(@RequestParam Payment payment) throws Exception {
        return ResponseEntity.ok().body(payment);
    }

    @PatchMapping("/updatePayment")
    public ResponseEntity<Object> updatePayment(@RequestParam Payment payment) throws Exception {
        return ResponseEntity.ok().body(payment);
    }

    @DeleteMapping("/deletePayment")
    public ResponseEntity<Object> deletePayment(@RequestParam int paymentId) {
        return ResponseEntity.ok().body(paymentId);
    }
}
