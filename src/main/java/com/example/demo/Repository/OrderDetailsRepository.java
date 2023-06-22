package com.example.demo.Repository;

import com.example.demo.DBConnection.DBUtils;
import com.example.demo.model.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderDetailsRepository {
    public static boolean createOrderDetails(OrderDetails orderDetails) throws Exception {
        try{
            List<CartItems> cartItemsList = orderDetails.getCartItemsList();

            //check product quantity
            for(int i = 0; i < cartItemsList.size(); i++) {
                CartItems cartItems = CartItemsRepository.getCartItemsById(cartItemsList.get(i).getCartItemId());
                int productId = cartItems.getProductId();
                int quantity = cartItems.getQuantity();
                Product product = ProductRepository.getProductById(productId);
                int newQuantity = product.getQuantity() - quantity;
                if (newQuantity < 0) return false;
            }

            //create new delivery
            String address = AddressRepository.getAddressById(orderDetails.getDeliveryAddressId()).getAddress();
            Delivery delivery = new Delivery(0, address);
            DeliveryRepository.createDelivery(delivery);
            List<Delivery> deliveryList = DeliveryRepository.getAllDelivery();
            delivery = deliveryList.get(deliveryList.size() - 1);

            //create new order
            int userId = orderDetails.getUserId();
            int paymentId = orderDetails.getPaymentId();
            int deliveryId = delivery.getDeliveryId();
            int statusId = 1;
            String note = orderDetails.getNote();
            int totalPayment = orderDetails.getTotalPayment();
            Order order = new Order(userId, paymentId, deliveryId, statusId, note, totalPayment);
            OrderRepository.createOrder(order);
            List<Order> orderList = OrderRepository.getAllOrder();
            order = orderList.get(orderList.size() - 1);

            //create order items
            int orderId = order.getOrderId();
            int cartItemId[] = new int[cartItemsList.size()]; //create int cartItemId to delete cartItems
            for(int i = 0; i < cartItemsList.size(); i++){
                CartItems cartItems = CartItemsRepository.getCartItemsById(cartItemsList.get(i).getCartItemId());
                cartItemId[i] = cartItems.getCartItemId(); //get cartItemId to delete cartItems
                int productId = cartItems.getProductId();
                int quantity = cartItems.getQuantity();
                OrderItem orderItem = new OrderItem(0, orderId, productId, quantity);
                OrderItemRepository.createOrderItem(orderItem);

                //update product quantity
                Product product = ProductRepository.getProductById(productId);
                int newQuantity = product.getQuantity() - quantity;
                product.setQuantity(newQuantity);
                ProductRepository.updateProduct(product);
            }
            CartItemsRepository.deleteCartItem(cartItemId);//delete cartItems
            return true;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public static List<OrderAndOrderItem> getAllOrderDetails(){
        List<OrderAndOrderItem> orderAndOrderItemList = new ArrayList<>();
        try {
            List<Order> orderList = OrderRepository.getAllOrder();
            for(int i = 0; i<orderList.size(); i++){
                //get order information
                Order order = orderList.get(i);
                int orderId = order.getOrderId();
                int userId = order.getUserId(); //get userId
                Payment payment = PaymentRepository.getPaymentById(order.getPaymentId()); //get payment
                Date orderDate = order.getOrderDate(); //get order date
                Delivery delivery = DeliveryRepository.getDeliveryById(order.getDeliveryId());
                OrderStatus orderStatus = OrderStatusRepository.getOrderStatusById(order.getStatusId());
                String note = order.getNote(); //get note
                int totalPayment = order.getTotalPayment(); //get total payment
                Date paymentDate = order.getPaymentDate(); //get payment date

                //get order Items List
                List<ProductAndOrderItem> productAndOrderItemList = OrderRepository.getProductAndOrderItem(orderId);

                //set order information
                OrderAndOrderItem orderAndOrderItem = new OrderAndOrderItem(orderId, userId, payment, orderDate, delivery, orderStatus, note, totalPayment, paymentDate, productAndOrderItemList); // create new orderDetails
                orderAndOrderItemList.add(orderAndOrderItem);
            }
            return orderAndOrderItemList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
