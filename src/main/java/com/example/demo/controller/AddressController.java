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
        List<Address> addressList = AddressRepository.getAllAddress();
        if(addressList.size()>0) return ResponseEntity.ok().body(addressList);
        else return ResponseEntity.badRequest().build();

    }

    @GetMapping("/getAddressByUserUid")
    public ResponseEntity<Object> getAddressByUserUid(@RequestParam String userUid) throws Exception {
        List<Address> addressList = AddressRepository.getAddressByUserUid(userUid);
        if(addressList.size()>0) return ResponseEntity.ok().body(addressList);
        else return ResponseEntity.badRequest().build();
    }


    @GetMapping("/getAddressById")
    public ResponseEntity<Object> getAddressById(@RequestParam int addressId) throws Exception {
        List<Address> addressList = AddressRepository.getAddressById(addressId);
        if(addressList.size()>0) return ResponseEntity.ok().body(addressList.get(0));
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/createAddress")
    public ResponseEntity<String> createAddress(@RequestBody Address address) throws Exception {
        return AddressRepository.createAddress(address);
    }

    @DeleteMapping("/deleteAddress")
    public ResponseEntity<String> deleteAddress(@RequestParam int[] addressId) throws Exception {
        return AddressRepository.deleteAddress(addressId);
    }

    @PatchMapping("/updateAddress")
    public ResponseEntity<String> updateAddress(@RequestBody Address adress) throws Exception {
        return AddressRepository.updateAddress(adress);
    }
}
