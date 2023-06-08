package com.example.demo.controller;

import com.example.demo.Repository.AddressRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.model.Address;
import com.example.demo.model.Cart;
import com.example.demo.model.CreateUser;
import com.example.demo.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @GetMapping("/getAllUser")
    public ResponseEntity<Object> getAllUser() throws Exception {
        List<User> userList = UserRepository.getAllUser();
        if (userList.size() > 0) return ResponseEntity.ok().body(userList);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getUserByUserId")
    public ResponseEntity<Object> getUserByUserId(@RequestParam int userId) throws Exception {
        User user = UserRepository.getUserByUserId(userId);
        if (user.getUserId() != 0) {
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getUserByUserUid")
    public ResponseEntity<Object> getUserByUserUid(@RequestParam String userUid) throws Exception {
        User user = UserRepository.getUserByUserUid(userUid);
        if (user.getUserUid() != null) {
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/createUser")
    public ResponseEntity<Object> createUser (@RequestBody CreateUser createUser) throws Exception {
        User user = new User(0, 0, createUser.getUserName(), createUser.getUserUid(), createUser.getEmail(), createUser.getPhoneNumber(), "");
        try{
            UserRepository.createUser(user);
            int userId = UserRepository.getUserByUserUid(createUser.getUserUid()).getUserId();
            Address address = new Address(userId, createUser.getAddress());
            AddressRepository.createAddress(address);
            return ResponseEntity.ok().body(createUser);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam int[] userId) throws Exception {
        return UserRepository.deleteUser(userId);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<String> updateUser(@RequestBody User user) throws Exception {
        return UserRepository.updateUser(user);
    }
}
