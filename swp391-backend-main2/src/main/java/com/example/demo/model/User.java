package com.example.demo.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    String userId;
    int userTypeId;
    String userName;
    String userPassword;
    String email;
    String phoneNumber;
    String note;
}
