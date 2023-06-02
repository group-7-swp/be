package com.example.demo.Repository;

import com.example.demo.DBConnection.DBUtils;
import com.example.demo.model.Delivery;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DeliveryRepository {
    public static Delivery getDeliveryByDeliveryId(int deliveryId) throws Exception {
        Delivery delivery = new Delivery();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "select * from dbo.Delivery where deliveryId = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, deliveryId);
            ResultSet table = pst.executeQuery();
            if (table != null){
                while (table.next()) {
                    delivery.setDeliveryId(table.getInt("deliveryId"));
                    delivery.setAddress(table.getString("address"));
                }
            }
        }
        return delivery;
    }

    public static ResponseEntity<String> createDelivery(Delivery delivery) throws Exception {
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "INSERT INTO Delivery (address) VALUES (?)";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, delivery.getAddress());
            int row = pst.executeUpdate();
            if (row > 0) {
                return ResponseEntity.ok().body("Create successfully");
            }
        }
        return ResponseEntity.badRequest().body("Create fail");
    }




}
