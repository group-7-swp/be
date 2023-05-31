package com.example.demo.Repository;

import com.example.demo.DBConnection.DBUtils;
import com.example.demo.model.Product;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    public static List<Product> getProduct(String sql) throws Exception {
        List<Product> productList = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    Product product = new Product();
                    product.setProductId(table.getInt("productId"));
                    product.setProductName(table.getString("productName"));
                    product.setPrice(table.getInt("price"));
                    product.setQuantity(table.getInt("quantity"));
                    product.setCategoryId(table.getInt("categoryId"));
                    product.setStatus(table.getString("status"));
                    product.setDescription(table.getString("description"));
                    product.setImage(table.getString("image"));
                    product.setCreateDate(table.getDate("createDate"));
                    productList.add(product);
                }
            }
        }
        return productList;
    }

    //Get all product
    public static List<Product> getAllProduct() throws Exception {
        List<Product> productList = getProduct("select * from dbo.Product");
        return productList;
    }

    //Search product by it's name
    public static List<Product> searchByName(String searchValue) throws Exception {
        String sql = "select * from dbo.Product where productName like '%" + searchValue + "%'";
        List<Product> productList = getProduct(sql);
        return productList;
    }

    //Filter product by it's category
    public static List<Product> filterByCategory(String categoryId) throws Exception {
        String sql = "select * from dbo.Product where categoryId = '" + categoryId + "'";
        List<Product> productList = getProduct(sql);
        return productList;
    }

    //Sort product by price
    public static List<Product> sortByPrice(String order) throws Exception {
        String sql = "Select * from dbo.Product order by price " + order;
        List<Product> productList = getProduct(sql);
        return productList;
    }

    //Filter product by status
    public static List<Product> filterByStatus(String status) throws Exception {
        String sql = "Select * from dbo.Product where status = N'" + status + "'";
        List<Product> productList = getProduct(sql);
        return productList;
    }

    //Get product quantity
    public static int getQuantity(int productId) throws Exception {
        String sql = "Select * from dbo.Product where productId = '" + productId + "'";
        int quantity = 0;
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                table.next();
                quantity = table.getInt("quantity");
            }
        }
        return quantity;
    }

    //Update new quantity when customer purchase product
    public static String updateQuantity(int buyQuantity, int productId) throws Exception {
        int oldQuantity = getQuantity(productId);
        int newQuantity = oldQuantity - buyQuantity;
        String sql = "UPDATE dbo.Product SET quantity = " + newQuantity + ", status = '' WHERE productId = '" + productId + "'";
        if (newQuantity == 0) {
            sql = "UPDATE dbo.Product SET quantity = 0, status = N'hết hàng' WHERE productId = '" + productId + "'";
        }
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            PreparedStatement pst = cn.prepareStatement(sql);
            int row = pst.executeUpdate();
            if (row > 0) {
                return "updated";
            }
        }
        return "failed";
    }

    //Add new product
}
