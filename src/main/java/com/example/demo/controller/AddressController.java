package com.example.demo.controller;

import com.example.demo.Repository.AddressRepository;
import com.example.demo.model.Address;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/address")
public class AddressController {
    @GetMapping("/allAddress")
    public ResponseEntity<Object> getAllAddress() throws Exception {
        List<Address> feedbackList = AddressRepository.getAllAddress();
        return ResponseEntity.ok().body(feedbackList);
    }

    @GetMapping("/getAddressById")
    public ResponseEntity<Object> getAddressById(@RequestParam int addressId) throws Exception {
        Address address = AddressRepository.getAddressById(addressId);
        return ResponseEntity.ok().body(address);
    }

    @PostMapping("/createAddress")
    public ResponseEntity<String> createAddress(@RequestBody Address adress) throws Exception {
        return AddressRepository.createAddress(adress);
    }

    @DeleteMapping("/deleteAddress")
    public ResponseEntity<String> deleteAddress(@RequestParam int addressId) throws Exception {
        return AddressRepository.deleteAddress(addressId);
    }

    @PatchMapping("/updateAddress")
    public ResponseEntity<String> updateAddress(@RequestBody Address adress) throws Exception {
        return AddressRepository.updateAddress(adress);
    }
}
