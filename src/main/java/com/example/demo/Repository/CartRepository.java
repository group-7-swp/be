package com.example.demo.Repository;

import com.example.demo.DBConnection.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

public class CartRepository {
    public static void deleteCart(int cartId, int userId, Date dateUpdate) throws Exception {
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "DELETE FROM Cart WHERE cartId = ? AND userId = ? AND dateUpdate = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, cartId);
            pst.setInt(2, userId);
            pst.setDate(3, new java.sql.Date(dateUpdate.getTime()));
            pst.executeUpdate();
        }
    }

}
