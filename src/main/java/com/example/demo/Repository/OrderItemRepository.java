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
                    orderItem.setOrderId(table.getInt("orderItemId"));
                    orderItem.setOrderId(table.getInt("orderId"));
                    orderItem.setProductId(table.getInt("productId"));
                    orderItem.setQuantity(table.getInt("quantity"));
                    orderItem.setTotalPrice(table.getInt("totalPrice"));
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
            String sql = "Select * from dbo.OrderItems where orderItemId = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, orderItemId);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrderId(table.getInt("orderItemId"));
                    orderItem.setOrderId(table.getInt("orderId"));
                    orderItem.setProductId(table.getInt("productId"));
                    orderItem.setQuantity(table.getInt("quantity"));
                    orderItem.setTotalPrice(table.getInt("totalPrice"));
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
                    orderItem.setOrderId(table.getInt("orderItemId"));
                    orderItem.setOrderId(table.getInt("orderId"));
                    orderItem.setProductId(table.getInt("productId"));
                    orderItem.setQuantity(table.getInt("quantity"));
                    orderItem.setTotalPrice(table.getInt("totalPrice"));
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
                    orderItem.setOrderId(table.getInt("orderId"));
                    orderItem.setProductId(table.getInt("productId"));
                    orderItem.setQuantity(table.getInt("quantity"));
                    orderItem.setTotalPrice(table.getInt("totalPrice"));
                    orderItemList.add(orderItem);
                }
            }
        }
        return orderItemList;
    }

    //Delete existing Order item by product id
    public static ResponseEntity<String> deleteOrderItem(int orderId, int productId) throws Exception {
        String sql = "Delete from dbo.OrderItems where orderId = ? and productId = ?";
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, orderId);
            pst.setInt(2, productId);
            int row = pst.executeUpdate();
            if (row > 0) {
                return ResponseEntity.ok().body("Delete successful");
            }
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
                pst.setInt(4, orderItem.getTotalPrice());
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
            String sql = "Update dbo.OrderItems set quantity = ?, totalPrice = ? where orderId = ? and productId = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, orderItem.getQuantity());
            pst.setInt(2, orderItem.getTotalPrice());
            pst.setInt(3, orderItem.getOrderId());
            pst.setInt(4, orderItem.getProductId());
            int row = pst.executeUpdate();
            if (row > 0) {
                return ResponseEntity.ok().body("Update successful");
            }
        }
        return ResponseEntity.badRequest().body("Failed");
    }


}
