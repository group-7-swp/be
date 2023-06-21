package com.example.demo.Repository;

import com.example.demo.DBConnection.DBUtils;
import com.example.demo.model.Order;
import com.example.demo.model.OrderAndOrderItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.Repository.OrderItemRepository.*;

public class OrderRepository {
    public static List<Order> getAllOrder() throws Exception {
        List<Order> orderList = new ArrayList<>();
        try {
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
                        order.setStatusId(table.getInt("statusId"));
                        order.setNote(table.getString("note"));
                        order.setTotalPayment(table.getInt("totalPayment"));
                        order.setPaymentDate(table.getDate("paymentDate"));
                        orderList.add(order);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return orderList;
    }

    //Get Order by Id
    public static Order getOrderById(int orderId) throws Exception {
        Order order = new Order();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Select * from dbo.Orders where orderId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, orderId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        order.setOrderId(table.getInt("orderId"));
                        order.setUserId(table.getInt("userId"));
                        order.setPaymentId(table.getInt("paymentId"));
                        order.setOrderDate(table.getDate("orderDate"));
                        order.setDeliveryId(table.getInt("deliveryId"));
                        order.setStatusId(table.getInt("statusId"));
                        order.setNote(table.getString("note"));
                        order.setTotalPayment(table.getInt("totalPayment"));
                        order.setPaymentDate(table.getDate("paymentDate"));
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return order;
    }

    //Delete existing Order by id
    public static boolean deleteOrder(int[] orderId) throws Exception {
        try{
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
                if (count > 0) return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean createOrder(Order order) throws Exception {
        try{
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql ="INSERT INTO Orders(userId, paymentId, orderDate, deliveryId, statusId, note, totalPayment)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?) ";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, order.getUserId());
                pst.setInt(2, order.getPaymentId());
                pst.setString(3, DBUtils.getCurrentDate());
                pst.setInt(4, order.getDeliveryId());
                pst.setInt(5, order.getStatusId());
                pst.setString(6, order.getNote());
                pst.setInt(7, order.getTotalPayment());
                //pst.setString(8, DBUtils.getCurrentDate());

                int row = pst.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    //Update existing product by id
    public static boolean updateOrder(Order Order) throws Exception {
        try{
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Update dbo.Orders set paymentId = ?, deliveryId = ?, status = ?, note = ?, totalPayment = ?, paymentDate = ? where OrderId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, Order.getPaymentId());
                pst.setInt(2, Order.getDeliveryId());
                pst.setInt(3, Order.getStatusId());
                pst.setString(4, Order.getNote());
                pst.setInt(5, Order.getTotalPayment());
                pst.setString(6, Order.getPaymentDate().toString());
                pst.setInt(7, Order.getOrderId());
                int row = pst.executeUpdate();
                if (row > 0) return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static List<OrderAndOrderItem> getOrderAndOrderItem() throws Exception {
        List<Order> orderList = getAllOrder();
        List<OrderAndOrderItem> orderAndOrderItemList = new ArrayList<>();
        for (int i = 0; i < orderList.size(); i++) {
            OrderAndOrderItem orderAndOrderItem = new OrderAndOrderItem();
            Order order = orderList.get(i);
            orderAndOrderItem.setOrder(order);
            orderAndOrderItem.setOrderItem(getOrderItemByOrderId(order.getOrderId()));
            orderAndOrderItemList.add(orderAndOrderItem);
        }
        return orderAndOrderItemList;
    }
}
