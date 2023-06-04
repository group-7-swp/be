package com.example.demo.Repository;

import com.example.demo.DBConnection.DBUtils;
import com.example.demo.model.Payment;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepository {
    public static List<Payment> getAllPayment() throws Exception {
        List<Payment> paymentList = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "Select * from Payment";
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    Payment payment = new Payment();
                    payment.setPaymentId(table.getInt("paymentId"));
                    payment.setPaymentType(table.getString("paymentType"));
                    paymentList.add(payment);
                }
            }
        }
        return paymentList;
    }

    public static List<Payment> getPaymentById(int paymentId) throws Exception {
        List<Payment> paymentList = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "Select * from Payment where paymentId = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, paymentId);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    Payment payment = new Payment();
                    payment.setPaymentId(table.getInt("paymentId"));
                    payment.setPaymentType(table.getString("paymentType"));
                    paymentList.add(payment);
                }
            }
        }
        return paymentList;
    }

    public static ResponseEntity<String> createPayment(Payment payment) throws Exception {
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "INSERT INTO Payment(paymentType) Values (?)";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, payment.getPaymentType());
            int row = pst.executeUpdate();
            if (row > 0) return ResponseEntity.ok().body("Create successful");
        }
        return ResponseEntity.badRequest().body("Failed");
    }

    public static ResponseEntity<String> deletePayment(int[] paymentId) throws Exception {
        Connection cn = DBUtils.makeConnection();
        int count = 0;
        if (cn != null) {
            for (int i = 0; i<paymentId.length; i++) {
                String sql = "Delete from Payment where paymentId";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, paymentId[i]);
                int row = pst.executeUpdate();
            }
            if (count > 0)return ResponseEntity.ok().body("Delete successful");
        }
        return ResponseEntity.badRequest().body("Failed");
    }
}
