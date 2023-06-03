package com.example.demo.Repository;

import com.example.demo.DBConnection.DBUtils;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PaymentRepository {
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
