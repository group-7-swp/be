package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.User;
import com.example.demo.model.UserError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.demo.DBUtil.DBUtils;

import javax.naming.NamingException;


@RestController
@RequestMapping("/api/user")
public class UserController {
    /*@GetMapping("")
    public ResponseEntity<Object>  login(@RequestParam String usernameLogin, String passwordLogin) {
        String username = "A";
        String password = "12345";
        //User user = new User(username, password);
        if(usernameLogin.equals(username) && passwordLogin.equals(password)){
            return ResponseEntity.ok().build();
        }
        else return ResponseEntity.badRequest().build();
    }*/
    @GetMapping("/login")
    public ResponseEntity<Object> checkLogin(@RequestParam String username, String password) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.makeConnection();
            String sql = "";
            if (con != null) {
                if (username.contains("@")) {
                    sql = "Select * From Users Where email = ? And userPassword = ?";
                } else {
                    sql = "Select * From Users Where userName = ? And userPassword = ?";
                }
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);

                rs = stm.executeQuery();
                if (rs != null && rs.next()) {
                    return ResponseEntity.ok().build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    //INFORMATION CHECK

    //Check if password entered with correct format
    public String passCheck(String str) {
        String regex = "^(?=.*\\d)(?=.*[a-zA-Z]).{8,20}$";
        boolean validPassword = isValid(str, regex);
        String passwordError = "";
        if ("".equals(str)) passwordError = "Bạn cần nhập \"Mật khẩu\" để đăng ký!";
        else if (!validPassword) passwordError = "Mật khẩu không hợp lệ! Vui lòng nhập lại.";
        return passwordError;
    }

    //Check if information entered with correct format
    public static boolean isValid(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    //Check if EMAIL is correct and entered and not duplicated
    public String checkEmail(String email) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        boolean validEmail = isValid(email, regex);
        String emailError = "";
        if ("".equals(email)) emailError = "Bạn cần nhập \"Email\" để đăng ký!";
        else if (!validEmail) emailError = "Email không hợp lệ! Vui lòng nhập lại.";
        else
            try {
                con = DBUtils.makeConnection();
                String sql = "";
                if (con != null) {
                    sql = "Select * From Users Where email = ?";

                    stm = con.prepareStatement(sql);
                    stm.setString(1, email);

                    rs = stm.executeQuery();
                    if (rs != null && rs.next()) {
                        emailError = "Email đã được sử dụng!";
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            }
        return emailError;
    }

    //Check if PHONE NUMBER is correct and entered and not duplicated
    public String checkPhone(String phoneNumber) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String regex = "^\\d{10}$";
        boolean validPhoneNumber = isValid(phoneNumber, regex);
        String phoneNumError = "";
        if ("".equals(phoneNumber)) phoneNumError = "Bạn cần nhập \"Số điện thoại\" để đăng ký!";
        else if (!validPhoneNumber) phoneNumError = "Số điện thoại không hợp lệ! Vui lòng nhập lại.";
        else
            try {
                con = DBUtils.makeConnection();
                String sql = "";
                if (con != null) {
                    sql = "Select * From Users Where phoneNumber = ?";

                    stm = con.prepareStatement(sql);
                    stm.setString(1, phoneNumber);

                    rs = stm.executeQuery();
                    if (rs != null && rs.next()) {
                        phoneNumError = "Số điện thoại đã được sử dụng!";
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            }
        return phoneNumError;
    }

    //Check if USERNAME is entered and not duplicated
    public String checkName(String userName) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String usernameError = "";
        if ("".equals(userName)) usernameError = "Bạn cần nhập \"Tên tài khoản\" để đăng ký!";
        else if (userName.length() > 30 || userName.length() < 4)
            usernameError = "Tên tài khoản không hợp lệ! Vui lòng nhập lại.";
        else
            try {
                con = DBUtils.makeConnection();
                String sql = "";
                if (con != null) {
                    sql = "Select * From Users Where userName = ?";

                    stm = con.prepareStatement(sql);
                    stm.setString(1, userName);

                    rs = stm.executeQuery();
                    if (rs != null && rs.next()) {
                        usernameError = "Tên tài khoản đã được sử dụng!";
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            }
        return usernameError;
    }

    //Create new user id Cxxxx, x is a number Eg: C0001
    public String getUserId()
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String id = "";

        try {
            con = DBUtils.makeConnection();

            if (con != null) {
                String sql = "Select top 1 userId from Users where userId like 'C%' order by userId desc";
                stm = con.prepareStatement(sql);

                rs = stm.executeQuery();
                rs.next();
                id = rs.getString("userId");
                int temp = Integer.parseInt(id.substring(1, 5)) + 1;
                id = "C" + String.format("%04d", temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return id;
    }

    //Insert new user
    public boolean insertUser(String userId, String userName, String userPassword, String email, String phoneNumber)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {

                String sql = "INSERT INTO Users (userId, userTypeId, userName, userPassword, email, phoneNumber, note)"
                        + "VALUES('" + userId + "', 3, N'" + userName + "', '" + userPassword + "', '" + email + "', '" + phoneNumber + "', '')";

                stm = con.prepareStatement(sql);
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public String getAddressId()
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String addressId = "";

        try {
            con = DBUtils.makeConnection();

            if (con != null) {
                String sql = "Select top 1 addressId from Address order by addressId desc";
                stm = con.prepareStatement(sql);

                rs = stm.executeQuery();
                rs.next();
                addressId = rs.getString("addressId");
                int temp = Integer.parseInt(addressId);
                addressId = String.format("%04d", temp + 1);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return addressId;
    }

    public boolean insertAddress(String userId, String address)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        String addressId = getAddressId();
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String currentDate = date.format(now);

        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO Address (userId, address, createDate)"
                        + "VALUES('" + userId + "', N'" + address + "', '" + currentDate + "')";

                stm = con.prepareStatement(sql);
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    @GetMapping("/register")
    public ResponseEntity<Object> createUser(@RequestParam String username, String password, String passConfirm, String email, String phoneNumber, String addressNumber, String ward, String district, String city)
            throws NamingException, SQLException {

        String passwordConfirmError = passCheck(passConfirm);
        if (!passConfirm.equals(password))
            passwordConfirmError = "Mật khẩu nhập lại không khớp với mật khẩu! Vui lòng nhập lại.";

        String passwordError = passCheck(password);

        String usernameError = checkName(username);

        String emailError = checkEmail(email);

        String phoneNumberError = checkPhone(phoneNumber);

        if (passwordConfirmError == "" && passwordError == "" && usernameError == "" && emailError == "" && passwordConfirmError == "" && phoneNumberError == "") {
            String userId = getUserId();
            boolean insertUser = insertUser(userId, username, password, email, phoneNumber);
            String address = addressNumber + ", Phường " + ward + ", Quận " + district + ", Thành phố " + city;
            boolean insertAddress = insertAddress(userId, address);
            if (insertUser && insertAddress) return ResponseEntity.ok().build();
            else return ResponseEntity.badRequest().build();
        } else {
            UserError error = new UserError(usernameError, passwordError, passwordConfirmError, emailError, phoneNumberError);
            return ResponseEntity.badRequest().body(error);
        }
    }
}

//http://localhost:8080/swagger-ui/#/