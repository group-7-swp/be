package com.example.demo.Repository;

import com.example.demo.DBConnection.DBUtils;
import com.example.demo.model.OrderItem;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderItemRepository {

    //Get all item in order
    public static List<OrderItem> getAllOrderItem() throws Exception {
        List<OrderItem> orderItemList = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "Select * from dbo.OrderItems";
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrderItemId(table.getInt("orderItemsId"));
                    orderItem.setOrderId(table.getInt("orderId"));
                    orderItem.setProductId(table.getInt("productId"));
                    orderItem.setQuantity(table.getInt("quantity"));
                    orderItemList.add(orderItem);
                }
            }
        }
        return orderItemList;
    }

    //Get Order by Id
    public static List<OrderItem> getOrderItemById(int orderItemId) throws Exception {
        List<OrderItem> orderItemList = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "Select * from dbo.OrderItems where orderItemsId = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, orderItemId);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrderItemId(table.getInt("orderItemsId"));
                    orderItem.setOrderId(table.getInt("orderId"));
                    orderItem.setProductId(table.getInt("productId"));
                    orderItem.setQuantity(table.getInt("quantity"));
                    orderItemList.add(orderItem);
                }
            }
        }
        return orderItemList;
    }

    public static List<OrderItem> getOrderItemByOrderId(int orderId) throws Exception {
        List<OrderItem> orderItemList = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "Select * from dbo.OrderItems where orderId = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, orderId);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrderId(table.getInt("orderItemsId"));
                    orderItem.setOrderId(table.getInt("orderId"));
                    orderItem.setProductId(table.getInt("productId"));
                    orderItem.setQuantity(table.getInt("quantity"));
                    orderItemList.add(orderItem);
                }
            }
        }
        return orderItemList;
    }

    //ko quan trọng
    public static List<OrderItem> getOrderItemByIdAndProductId(int orderId, int productId) throws Exception {
        List<OrderItem> orderItemList = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "Select * from dbo.OrderItems where orderId = ? and productId = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, orderId);
            pst.setInt(2, productId);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrderId(table.getInt("orderItemsId"));
                    orderItem.setOrderId(table.getInt("orderId"));
                    orderItem.setProductId(table.getInt("productId"));
                    orderItem.setQuantity(table.getInt("quantity"));
                    orderItemList.add(orderItem);
                }
            }
        }
        return orderItemList;
    }

    //Delete existing Order item by product id
    public static ResponseEntity<String> deleteOrderItem(int[] orderItemId) throws Exception {
        String sql = "Delete from dbo.OrderItems where orderItemsId = ?";
        Connection cn = DBUtils.makeConnection();
        int count = 0;
        if (cn != null) {
            for (int i = 0; i<orderItemId.length; i++) {
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, orderItemId[i]);
                int row = pst.executeUpdate();
                if (row > 0) count++;
            }
            if (count > 0)return ResponseEntity.ok().body("Delete successful");
        }
        return ResponseEntity.badRequest().body("Failed");
    }

    public static ResponseEntity<String> createOrderItem(OrderItem orderItem) throws Exception {
        if(getOrderItemByIdAndProductId(orderItem.getOrderId(), orderItem.getProductId()).size() == 0) {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "INSERT INTO OrderItems(orderId, productId, quantity, totalPrice)" +
                        "VALUES (?, ?, ?, ?) ";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, orderItem.getOrderId());
                pst.setInt(2, orderItem.getProductId());
                pst.setInt(3, orderItem.getQuantity());
                int row = pst.executeUpdate();
                if (row > 0) {
                    return ResponseEntity.ok().body("Create successful");
                }
            }
        }
        return ResponseEntity.badRequest().body("Failed");
    }

    //Update existing product by id
    public static ResponseEntity<String> updateOrderItem(OrderItem orderItem) throws Exception {
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "Update dbo.OrderItems set quantity = ?, totalPrice = ? where orderItemsId = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, orderItem.getQuantity());
            pst.setInt(2, orderItem.getOrderItemId());
            int row = pst.executeUpdate();
            if (row > 0) {
                return ResponseEntity.ok().body("Update successful");
            }
        }
        return ResponseEntity.badRequest().body("Failed");
    }


}
