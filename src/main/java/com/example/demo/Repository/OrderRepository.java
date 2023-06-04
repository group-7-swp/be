package com.example.demo.Repository;

import com.example.demo.DBConnection.DBUtils;
import com.example.demo.model.Order;
import com.example.demo.model.OrderAndOrderItem;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.Repository.OrderItemRepository.getOrderItemById;

public class OrderRepository {
    public static List<Order> getAllOrder() throws Exception {
        List<Order> orderList = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "Select * from dbo.Orders";
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    Order order = new Order();
                    order.setOrderId(table.getInt("orderId"));
                    order.setUserId(table.getInt("userId"));
                    order.setPaymentId(table.getInt("paymentId"));
                    order.setOrderDate(table.getDate("orderDate"));
                    order.setDeliveryId(table.getInt("deliveryId"));
                    order.setStatus(table.getString("status"));
                    order.setNote(table.getString("note"));
                    order.setTotalPayment(table.getInt("totalPayment"));
                    order.setPaymentDate(table.getDate("paymentDate"));
                    orderList.add(order);
                }
            }
        }
        return orderList;
    }

    //Get Order by Id
    public static List<Order> getOrderById(int orderId) throws Exception {
        List<Order> orderList = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "Select * from dbo.Orders where orderId = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, orderId);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    Order order = new Order();
                    order.setOrderId(table.getInt("orderId"));
                    order.setUserId(table.getInt("userId"));
                    order.setPaymentId(table.getInt("paymentId"));
                    order.setOrderDate(table.getDate("orderDate"));
                    order.setDeliveryId(table.getInt("deliveryId"));
                    order.setStatus(table.getString("status"));
                    order.setNote(table.getString("note"));
                    order.setTotalPayment(table.getInt("totalPayment"));
                    order.setPaymentDate(table.getDate("paymentDate"));
                    orderList.add(order);
                }
            }
        }
        return orderList;
    }

    //Delete existing Order by id
    public static ResponseEntity<String> deleteOrder(int[] orderId) throws Exception {
        String sql = "Delete from dbo.Orders where orderId = ?";
        Connection cn = DBUtils.makeConnection();
        int count = 0;
        if (cn != null) {
            for (int i = 0; i<orderId.length; i++) {
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, orderId[i]);
                int row = pst.executeUpdate();
                if (row > 0) count++;
            }
            if (count > 0)return ResponseEntity.ok().body("Delete successful");
        }
        return ResponseEntity.badRequest().body("Failed");
    }

    public static ResponseEntity<String> createOrder(Order Order) throws Exception {
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql ="INSERT INTO Orders(userId, paymentId, orderDate, deliveryId, status, note, totalPayment)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?) ";

            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, Order.getUserId());
            pst.setInt(2, Order.getPaymentId());
            pst.setString(3, DBUtils.getCurrentDate());
            pst.setInt(4, Order.getDeliveryId());
            pst.setString(5, Order.getStatus());
            pst.setString(6, Order.getNote());
            pst.setInt(7, Order.getTotalPayment());
            //pst.setString(8, DBUtils.getCurrentDate());

            int row = pst.executeUpdate();
            if (row > 0) {
                return ResponseEntity.ok().body("Create successful");
            }
        }
        return ResponseEntity.badRequest().body("Failed");
    }

    //Update existing product by id
    public static ResponseEntity<String> updateOrder(Order Order) throws Exception {

        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "Update dbo.Orders set paymentId = ?, deliveryId = ?, status = ?, note = ?, totalPayment = ?, paymentDate = ? where OrderId = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, Order.getPaymentId());
            pst.setInt(2, Order.getDeliveryId());
            pst.setString(3, Order.getStatus());
            pst.setString(4, Order.getNote());
            pst.setInt(5, Order.getTotalPayment());
            pst.setString(6, Order.getPaymentDate().toString());
            pst.setInt(7, Order.getOrderId());
            int row = pst.executeUpdate();
            if (row > 0) {
                return ResponseEntity.ok().body("Update successful");
            }
        }
        return ResponseEntity.badRequest().body("Failed");
    }

    public static List<OrderAndOrderItem> getOrderAndOrderItem() throws Exception {
        List<Order> orderList = getAllOrder();
        List<OrderAndOrderItem> orderAndOrderItemList = new ArrayList<>();
        for (int i = 0; i < orderList.size(); i++) {
            OrderAndOrderItem orderAndOrderItem = new OrderAndOrderItem();
            Order order = orderList.get(i);
            orderAndOrderItem.setOrder(order);
            orderAndOrderItem.setOrderItem(getOrderItemById(order.getOrderId()));
            orderAndOrderItemList.add(orderAndOrderItem);
        }
        return orderAndOrderItemList;
    }
}
