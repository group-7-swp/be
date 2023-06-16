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
public class Order {
    private int orderId;
    private int userId;
    private int paymentId;
    private Date orderDate;
    private int deliveryId;
    private String status;
    private String note;
    private Date paymentDate;
}
