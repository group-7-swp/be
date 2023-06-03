package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address {
    private int addressId;
    int userId;
    String address;
    private Date dateCreate;
    private Date dateUpdate;
}
