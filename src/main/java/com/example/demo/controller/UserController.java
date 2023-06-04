package com.example.demo.controller;

import com.example.demo.Repository.UserRepository;
import com.example.demo.model.Address;
import com.example.demo.model.Cart;
import com.example.demo.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
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
    public ResponseEntity<String> createUser (@RequestBody User user) throws Exception {
        return UserRepository.createUser(user);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam int[] userId) throws Exception {
        return UserRepository.deleteUser(userId);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<String> updateUser(@RequestParam User user) throws Exception {
        return UserRepository.updateUser(user);
    }
}
