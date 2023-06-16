package com.example.demo.controller;

import com.example.demo.Repository.AddressRepository;
import com.example.demo.Repository.DeliveryRepository;
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
    public ResponseEntity<String> createPayment(@RequestBody Payment payment) throws Exception {
        if(PaymentRepository.createPayment(payment)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/updatePayment")
    public ResponseEntity<String> updatePayment(@RequestBody Payment payment) throws Exception {
        if(PaymentRepository.updatePayment(payment)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/deletePayment")
    public ResponseEntity<String> deletePayment(@RequestParam int[] paymentId) throws Exception {
        if(PaymentRepository.deletePayment(paymentId)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }
}
