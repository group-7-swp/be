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
            String sql = "SELECT * FROM dbo.Payments";
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

    public static Payment getPaymentById(int paymentId) throws Exception {
        Payment payment = new Payment();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "SELECT * FROM Payments WHERE paymentId = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, paymentId);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    payment.setPaymentId(table.getInt("paymentId"));
                    payment.setPaymentType(table.getString("paymentType"));
                }
            }
        }
        return payment;
    }


    public static ResponseEntity<String> createPayment(Payment payment) throws Exception {
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "INSERT INTO Payments (paymentId, paymentType) VALUES (?, ?)";

            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, payment.getPaymentId());
            pst.setString(2, payment.getPaymentType());
            int row = pst.executeUpdate();
            if (row > 0) {
                return ResponseEntity.ok().body("Create successful");
            }
            cn.close();
        }
        return ResponseEntity.badRequest().body("Failed");
    }

    public static ResponseEntity<String> updatePayment(Payment payment) throws Exception {
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "UPDATE Payments SET paymentType = ? WHERE paymentId = ?";

            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, payment.getPaymentType());
            pst.setInt(2, payment.getPaymentId());

            int row = pst.executeUpdate();
            if (row > 0) {
                return ResponseEntity.ok().body("Update successful");
            }
            cn.close();
        }
        return ResponseEntity.badRequest().body("Failed");
    }


    public static ResponseEntity<String> deletePayment(int paymentId, String paymentType) throws Exception {
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "Delete from Payment where paymentId = ? AND paymentType = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, paymentId);
            pst.setString(2, paymentType);
            int row = pst.executeUpdate();
            if (row > 0) {
                return ResponseEntity.ok().body("Delete successful");
            }
        }
        return ResponseEntity.badRequest().body("Delete failed");
    }


}
