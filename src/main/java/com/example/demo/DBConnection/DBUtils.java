package com.example.demo.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DBUtils {
    public static Connection makeConnection() throws Exception{
        Connection cn=null;
        String uid="db_a9a498_tiemhommie_admin";
        String pwd="swp391project";
        String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url="jdbc:sqlserver://SQL8003.site4now.net;databaseName=db_a9a498_tiemhommie;user="+uid+";password="+pwd;
        Class.forName(driver);
        cn=DriverManager.getConnection(url);
        return cn;
    }
    public static String getCurrentDate(){
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String currentDate = date.format(now);
        return currentDate;
    }
}
