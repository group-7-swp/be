package com.example.demo.Repository;

import com.example.demo.DBConnection.DBUtils;
import com.example.demo.model.Delivery;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryRepository {
    public static List<Delivery> getAllDelivery() throws Exception {
        List<Delivery> deliveryList = new ArrayList<>();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "select * from dbo.Delivery";
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        Delivery delivery = new Delivery();
                        delivery.setDeliveryId(table.getInt("deliveryId"));
                        delivery.setAddressId(table.getInt("addressId"));
                        deliveryList.add(delivery);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return deliveryList;
    }

    public static Delivery getDeliveryById(int deliveryId) throws Exception {
        Delivery delivery = new Delivery();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "select * from dbo.Delivery where deliveryId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, deliveryId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        delivery.setDeliveryId(table.getInt("deliveryId"));
                        delivery.setAddressId(table.getInt("addressId"));
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return delivery;
    }

    public static boolean createDelivery(Delivery delivery) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "INSERT INTO Delivery (addressId) VALUES (?)";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, delivery.getAddressId());
                int row = pst.executeUpdate();
                if (row > 0) return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteDelivery(int[] deliveryId) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            int count = 0;
            if (cn != null) {
                for (int i = 0; i < deliveryId.length; i++) {
                    String sql = "Delete from Delivery where deliveryId = ?";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, deliveryId[i]);
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

    public static boolean updateDelivery(Delivery delivery) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Update Delivery set addressId = ? where deliveryId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, delivery.getAddressId());
                pst.setInt(2, delivery.getDeliveryId());
                int row = pst.executeUpdate();
                if (row > 0) return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /*public static Delivery getDeliveryByAddressId(int addressId) throws Exception {
        Delivery delivery = new Delivery();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "select * from dbo.Delivery where addressId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, addressId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        delivery.setDeliveryId(table.getInt("deliveryId"));
                        delivery.setAddressId(table.getInt("addressId"));
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return delivery;
    }*/
}
