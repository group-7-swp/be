package com.example.demo.Repository;

import com.example.demo.DBConnection.DBUtils;
import com.example.demo.model.Cart;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartRepository {
    public static List<Cart> getAllCart() throws Exception {
        List<Cart> cartList = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "SELECT * FROM dbo.Carts";
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    Cart cart = new Cart();
                    cart.setCartId(table.getInt("cartId"));
                    cart.setUserId(table.getInt("userId"));
                    cart.setDateUpdate(table.getDate("dateUpdate"));
                    cartList.add(cart);
                }
            }
        }
        return cartList;
    }

    public static List<Cart> getCartByCartId(int cartId) throws Exception {
        List<Cart> cartList = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "SELECT * FROM Carts WHERE cartId = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, cartId);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    Cart cart = new Cart();
                    cart.setCartId(table.getInt("cartId"));
                    cart.setUserId(table.getInt("userId"));
                    cart.setDateUpdate(table.getDate("dateUpdate"));
                    cartList.add(cart);
                }
            }
        }
        return cartList;
    }

    public static List<Cart> getCartByUserId(int cartId) throws Exception {
        List<Cart> cartList = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "SELECT * FROM Carts WHERE userId = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, cartId);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    Cart cart = new Cart();
                    cart.setCartId(table.getInt("cartId"));
                    cart.setUserId(table.getInt("userId"));
                    cart.setDateUpdate(table.getDate("dateUpdate"));
                    cartList.add(cart);
                }
            }
        }
        return cartList;
    }



    //Create a new cart
    public static ResponseEntity<String> createCart(Cart cart) throws Exception {
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "INSERT INTO Carts (userId, dateUpdate) VALUES (?, ?)";

            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, cart.getUserId());
            pst.setString(2, DBUtils.getCurrentDate());

            int row = pst.executeUpdate();
            if (row > 0) {
                return ResponseEntity.ok().body("Create successful");
            }
        }
        return ResponseEntity.badRequest().body("Failed");
    }

    //Delete Cart
    public static ResponseEntity<String> deleteCart(int[] cartId) throws Exception {
        Connection cn = DBUtils.makeConnection();
        int count = 0;
        if (cn!= null) {
            for (int i = 0; i<cartId.length; i++) {
                String sql = "DELETE FROM Cart WHERE cartId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, cartId[i]);
                int row = pst.executeUpdate();
                if(row > 0) count++;
            }
            if (count > 0) return ResponseEntity.ok().body("Delete Successfully");
        }
        return ResponseEntity.badRequest().body("Delete Fail");
    }
}
