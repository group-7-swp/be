package com.example.demo.model.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUser {
    private String userName;
    private String userUid;
    private String email;
    private String phoneNumber;
}
