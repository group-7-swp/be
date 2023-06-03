package com.example.demo.Repository;

import com.example.demo.DBConnection.DBUtils;
import com.example.demo.model.CartItems;
import com.example.demo.model.Category;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartItemsRepository {
    public static List<CartItems> getAllCartItems(int cartId) throws Exception {
        List<CartItems> cartItemsList = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "SELECT * FROM CartItems WHERE cartId = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, cartId);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    CartItems cartItems = new CartItems();
                    cartItems.setCartId(table.getInt("cartId"));
                    cartItems.setProductId(table.getInt("productId"));
                    cartItems.setQuantity(table.getInt("quantity"));
                    cartItems.setTotalPrice(table.getDouble("totalPrice"));
                    cartItemsList.add(cartItems);
                }
            }
        }
        return cartItemsList;
    }

    public static List<CartItems> getCartItemsByCartId(int cartId) throws Exception {
        List<CartItems> cartItemsList = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "SELECT * FROM CartItems WHERE cartId = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, cartId);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    CartItems cartItems = new CartItems();
                    cartItems.setCartId(table.getInt("cartId"));
                    cartItems.setProductId(table.getInt("productId"));
                    cartItems.setQuantity(table.getInt("quantity"));
                    cartItems.setTotalPrice(table.getDouble("totalPrice"));
                    cartItemsList.add(cartItems);
                }
            }
        }
        return cartItemsList;
    }

    //Create cartItems
    public static ResponseEntity<String> createCartItems(int cartId, int productId, int quantity, double totalPrice) throws Exception {
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "INSERT INTO CartItems (cartId, productId, quantity, totalPrice) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, cartId);
            pst.setInt(2, productId);
            pst.setInt(3, quantity);
            pst.setDouble(4, totalPrice);
            int row = pst.executeUpdate();
            if (row > 0) {
                return ResponseEntity.ok().body("Create successful");
            }
        }
        return ResponseEntity.badRequest().body("Create failed");
    }

    //Update cartItems
    public static ResponseEntity<String> updateCartItems(int cartId, int productId, int quantity, double totalPrice) throws Exception {
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "UPDATE CartItems SET quantity = ?, totalPrice = ? WHERE cartId = ? AND productId = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, quantity);
            pst.setDouble(2, totalPrice);
            pst.setInt(3, cartId);
            pst.setInt(4, productId);
            int row = pst.executeUpdate();
            if (row > 0) {
                return ResponseEntity.ok().body("Update successful");
            }
        }
        return ResponseEntity.badRequest().body("Update failed");
    }

    //Delete cartItems
    public static ResponseEntity<String> deleteCartItem(int cartId, int productId, int quantity, double totalPrice) throws Exception {
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "DELETE FROM CartItem WHERE cartId = ? AND productId = ? AND quantity = ? AND totalPrice = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, cartId);
            pst.setInt(2, productId);
            pst.setInt(3, quantity);
            pst.setDouble(4, totalPrice);
            int row = pst.executeUpdate();
            if (row > 0) {
                return ResponseEntity.ok().body("Delete successful");
            }
        }
        return ResponseEntity.badRequest().body("Delete failed");
    }



}
