package com.example.demo.Repository;

import com.example.demo.DBConnection.DBUtils;
import com.example.demo.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserRepository {
    public static User getUserByUserId(int userId) throws Exception {
        User user = new User();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "select * from dbo.Users where userId = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, userId);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    user.setUserId(table.getInt("userId"));
                    user.setUserRole(table.getInt("userRole"));
                    user.setUserName(table.getString("userName"));
                    user.setUserUid(table.getString("userUid"));
                    user.setEmail(table.getString("email"));
                    user.setPhoneNumber(table.getInt("phoneNumber"));
                    user.setNote(table.getString("note"));
                }
            }
        }
        return user;
    }
}
