package com.example.demo.Repository;

import com.example.demo.DBConnection.DBUtils;
import com.example.demo.model.Payment;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepository {
    public static List<Payment> getAllPayment() throws Exception {
        List<Payment> paymentList = new ArrayList<>();
        try{
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
        }catch (SQLException e){
            e.printStackTrace();
        }
        return paymentList;
    }

    public static Payment getPaymentById(int paymentId) throws Exception {
        Payment payment = new Payment();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Select * from Payment where paymentId = ?";
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
        }catch (SQLException e){
            e.printStackTrace();
        }
        return payment;
    }

    public static boolean createPayment(Payment payment) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "INSERT INTO Payment(paymentType) Values (?)";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, payment.getPaymentType());
                int row = pst.executeUpdate();
                if (row > 0) return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deletePayment(int[] paymentId) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            int count = 0;
            if (cn != null) {
                for (int i = 0; i < paymentId.length; i++) {
                    String sql = "Delete from Payment where paymentId = ?";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, paymentId[i]);
                    int row = pst.executeUpdate();
                    if (row > 0) count++;
                }
                if (count > 0) return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePayment(Payment payment) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "UPDATE Payment SET paymentType = ? WHERE paymentId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, payment.getPaymentType());
                pst.setInt(2, payment.getPaymentId());
                int row = pst.executeUpdate();
                if (row > 0) return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
