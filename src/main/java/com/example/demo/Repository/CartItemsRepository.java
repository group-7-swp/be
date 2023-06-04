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
    public static List<CartItems> getAllCartItems() throws Exception {
        List<CartItems> cartItemsList = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "SELECT * FROM CartItems";
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    CartItems cartItems = new CartItems();
                    cartItems.setCartItemId(table.getInt("cartItemId"));
                    cartItems.setCartId(table.getInt("cartId"));
                    cartItems.setProductId(table.getInt("productId"));
                    cartItems.setQuantity(table.getInt("quantity"));
                    cartItems.setTotalPrice(table.getInt("totalPrice"));
                    cartItemsList.add(cartItems);
                }
            }
        }
        return cartItemsList;
    }

    public static List<CartItems> getCartItemsById(int cartItemId) throws Exception {
        List<CartItems> cartItemsList = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "SELECT * FROM CartItems WHERE cartItemId = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, cartItemId);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    CartItems cartItems = new CartItems();
                    cartItems.setCartItemId(table.getInt("cartItemId"));
                    cartItems.setCartId(table.getInt("cartId"));
                    cartItems.setProductId(table.getInt("productId"));
                    cartItems.setQuantity(table.getInt("quantity"));
                    cartItems.setTotalPrice(table.getInt("totalPrice"));
                    cartItemsList.add(cartItems);
                }
            }
        }
        return cartItemsList;
    }

    //Create cartItems
    public static ResponseEntity<String> createCartItems(CartItems cartItem) throws Exception {
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "INSERT INTO CartItems (cartId, productId, quantity, totalPrice) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, cartItem.getCartId());
            pst.setInt(2, cartItem.getProductId());
            pst.setInt(3, cartItem.getQuantity());
            pst.setInt(4, cartItem.getTotalPrice());
            int row = pst.executeUpdate();
            if (row > 0) {
                return ResponseEntity.ok().body("Create successful");
            }
        }
        return ResponseEntity.badRequest().body("Create failed");
    }

    //Update cartItems
    public static ResponseEntity<String> updateCartItems(CartItems cartItem) throws Exception {
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "UPDATE CartItems SET quantity = ?, totalPrice = ? WHERE cartItemId = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, cartItem.getQuantity());
            pst.setDouble(2, cartItem.getTotalPrice());
            pst.setInt(3, cartItem.getCartItemId());
            int row = pst.executeUpdate();
            if (row > 0) {
                return ResponseEntity.ok().body("Update successful");
            }
        }
        return ResponseEntity.badRequest().body("Update failed");
    }

    //Delete cartItems
    public static ResponseEntity<String> deleteCartItem(int[] cartItemId) throws Exception {
        Connection cn = DBUtils.makeConnection();
        int count = 0;
        if (cn != null) {
            for (int i = 0; i<cartItemId.length; i++) {
                String sql = "DELETE FROM CartItems WHERE cartItemId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, cartItemId[i]);
                int row = pst.executeUpdate();
                if(row > 0) count++;
            }
            if (count > 0) return ResponseEntity.ok().body("Delete Successfully");
        }
        return ResponseEntity.badRequest().body("Delete Fail");
    }
}
