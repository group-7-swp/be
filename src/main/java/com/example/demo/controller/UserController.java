package com.example.demo.controller;

import com.example.demo.Repository.UserRepository;
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

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody User user) throws Exception {
        return UserRepository.createUser(user);
    }
}
