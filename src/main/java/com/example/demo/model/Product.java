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
public class Product {
    int productId;
    String productName;
    int price;
    int quantity;
    int categoryId;
    String status;
    String description;
    String image;
    Date createDate;
}
