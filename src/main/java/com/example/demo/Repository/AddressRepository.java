package com.example.demo.Repository;

import com.example.demo.DBConnection.DBUtils;
import com.example.demo.model.Address;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AddressRepository {
    public static List<Address> getAllAddress() throws Exception {
        List<Address> addressList = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null){
            String sql = "Select * from dbo.Address";
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    Address address = new Address();
                    address.setAddressId(table.getInt("addressId"));
                    address.setUserId(table.getInt("userId"));
                    address.setAddress(table.getString("address"));
                    address.setDateCreate(table.getDate("dateCreate"));
                    address.setDateUpdate(table.getDate("dateUpdate"));
                    addressList.add(address);
                }
            }
        } return addressList;
    }

    public static Address getAddressById(int addressId) throws Exception {
        Address address = new Address();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "Select * from dbo.Address where addressId = ? ";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, addressId);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    address.setAddressId(table.getInt("addressId"));
                    address.setUserId(table.getInt("userId"));
                    address.setAddress(table.getString("address"));
                    address.setDateCreate(table.getDate("dateCreate"));
                    address.setDateUpdate(table.getDate("dateUpdate"));
                }
            }
        }
        return address;
    }

    //Create new Category
    public static ResponseEntity<String> createAddress(Address address) throws Exception {
        Address category = new Address();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "INSERT INTO dbo.Address (userId, address, dateCreate) VALUES (?,?,?)";
            PreparedStatement pst = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, address.getUserId());
            pst.setString(2, address.getAddress());
            pst.setString(3, DBUtils.getCurrentDate());
            int row = pst.executeUpdate();

            if (row > 0) {
                return ResponseEntity.ok().body("Create successful");
            }
        }
        return ResponseEntity.badRequest().body("Failed");
    }

    //Update Category
    public static ResponseEntity<String> updateAddress(Address address) throws Exception {
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "Update dbo.Address Set address = ?, dateUpdate = ? WHERE addressId = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, address.getAddress());
            pst.setString(2, DBUtils.getCurrentDate());
            pst.setInt(3, address.getAddressId());
            pst.executeUpdate();
            int row = pst.executeUpdate();

            if (row > 0) {
                return ResponseEntity.ok().body("Update successful");
            }
        }
        return ResponseEntity.badRequest().body("Failed");
    }

    //Delete Category
    public static ResponseEntity<String> deleteAddress(int addressId) throws Exception {
        Connection cn = DBUtils.makeConnection();
        if (cn!= null) {
            String sql = "Delete from dbo.Address where addressId = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, addressId);
            int row = pst.executeUpdate();
            if (row > 0) {
                return ResponseEntity.ok().body("Delete Successfully");
            }
        }
        return ResponseEntity.badRequest().body("Delete Fail");
    }
}
