package com.example.demo.DBUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    public static Connection makeConnection(){
        try {
            String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String url = "jdbc:sqlserver://localhost:1433;databasename=E_Gift_Shop;instanceName=HIEU;";
            Class.forName(driver);
            Connection cn= DriverManager.getConnection(url, "sa", "12345");
            return cn;
        } catch(ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
