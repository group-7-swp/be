package com.example.demo.Repository;

import com.example.demo.model.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsRepositiry {
    public static boolean createOrderDetails(OrderDetails orderDetails) throws Exception {
        try{
            List<CartItems> cartItemsList = orderDetails.getCartItemsList();

            //check product quantity
            for(int i = 0; i < cartItemsList.size(); i++) {
                CartItems cartItems = cartItemsList.get(i);
                int productId = cartItems.getProductId();
                int quantity = cartItems.getQuantity();
                Product product = ProductRepository.getProductById(productId);
                int newQuantity = product.getQuantity() - quantity;
                if (newQuantity < 0) return false;
            }

            //create new delivery
            Delivery delivery = new Delivery(0, orderDetails.getDeliveryAddressId());
            DeliveryRepository.createDelivery(delivery);
            List<Delivery> deliveryList = DeliveryRepository.getAllDelivery();
            delivery = deliveryList.get(deliveryList.size() - 1);

            //create new order
            int userId = orderDetails.getUserId();
            int paymentId = orderDetails.getPaymentId();
            int deliveryId = delivery.getDeliveryId();
            String status ="Processing";
            String note = orderDetails.getNote();
            int totalPayment = orderDetails.getTotalPayment();
            Order order = new Order(userId, paymentId, deliveryId, status, note, totalPayment);
            OrderRepository.createOrder(order);
            List<Order> orderList = OrderRepository.getAllOrder();
            order = orderList.get(orderList.size() - 1);

            //create order items
            int orderId = order.getOrderId();
            int cartItemId[] = new int[cartItemsList.size()]; //create int cartItemId to delete cartItems
            for(int i = 0; i < cartItemsList.size(); i++){
                CartItems cartItems = cartItemsList.get(i);
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
}
