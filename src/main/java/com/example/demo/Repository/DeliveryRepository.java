package com.example.demo.Repository;

import com.example.demo.DBConnection.DBUtils;
import com.example.demo.model.Address;
import com.example.demo.model.Delivery;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DeliveryRepository {
<<<<<<< Updated upstream
    public static List<Delivery> getAllDelivery(int deliveryId, String address) throws Exception {
        List<Delivery> deliveryList = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "SELECT * FROM Delivery WHERE paymentId = ? AND address = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, deliveryId);
            pst.setString(2, address);
            ResultSet table = pst.executeQuery();
            if (table != null) {
=======
    public static List<Delivery> getAllDelivery() throws Exception {
        List<Delivery> deliveryList = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "select * from dbo.Delivery";
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet table = pst.executeQuery();
            if (table != null){
>>>>>>> Stashed changes
                while (table.next()) {
                    Delivery delivery = new Delivery();
                    delivery.setDeliveryId(table.getInt("deliveryId"));
                    delivery.setAddress(table.getString("address"));
                    deliveryList.add(delivery);
                }
            }
<<<<<<< Updated upstream
            cn.close();
=======
>>>>>>> Stashed changes
        }
        return deliveryList;
    }

<<<<<<< Updated upstream
    public static Delivery getDeliveryByDeliveryId(int deliveryId) throws Exception {
=======
    public static Delivery getDeliveryById(int deliveryId) throws Exception {
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
    public static ResponseEntity<String> updateDelivery(int paymentId, Address address) throws Exception {
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "UPDATE Delivery SET address = ? WHERE paymentId = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, address.getAddress());
            pst.setInt(2, paymentId);
            int row = pst.executeUpdate();
            if (row > 0) {
                cn.close();
                return ResponseEntity.ok().body("Update successful");
            }
            cn.close();
        }
        return ResponseEntity.badRequest().body("Failed");
    }

    public static ResponseEntity<String> deleteDelivery(int deliveryId, String address) throws Exception {
=======
    public static ResponseEntity<String> deleteDelivery(int[] deliveryId) throws Exception {
        Connection cn = DBUtils.makeConnection();
        int count = 0;
        if (cn!= null) {
            for (int i = 0; i<deliveryId.length; i++) {
                String sql = "Delete from Delivery where deliveryId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, deliveryId[i]);
                int row = pst.executeUpdate();
                if(row > 0) count++;
            }
            if (count > 0) return ResponseEntity.ok().body("Delete Successfully");
        }
        return ResponseEntity.badRequest().body("Delete Fail");
    }

    public static ResponseEntity<String> updateDelivery(Delivery delivery) throws Exception {
>>>>>>> Stashed changes
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "Update Delivery set address = ? where deliveryId = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, delivery.getAddress());
            pst.setInt(2, delivery.getDeliveryId());
            int row = pst.executeUpdate();
            if (row > 0) {
                return ResponseEntity.ok().body("Update successful");
            }
        }
        return ResponseEntity.badRequest().body("Update failed");
    }

}
