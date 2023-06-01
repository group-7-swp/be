package com.example.demo.Repository;

import com.example.demo.DBConnection.DBUtils;
import com.example.demo.model.Product;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                    product.setDateCreate(table.getDate("dateCreate"));
                    product.setDateUpdate(table.getDate("dateUpdate"));
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

    //Filter product by id
    public static List<Product> getProductById(String productId) throws Exception {
        String sql = "select * from dbo.Product where productId = '" + productId + "'";
        List<Product> productList = getProduct(sql);
        return productList;
    }

    //Multiple filter
    public static List<Product> multiFilter(boolean category, boolean price, int categoryId, int from, int to, String status) throws Exception {
        String sql = "Select * from dbo.Product where ";
        if (category) sql = sql + "categoryId = " + categoryId + " and ";
        if (price) sql = sql + "price >= " + from + " and price <= " + to + " and ";
        if (status!=null) sql = sql + "status = N'" + status + "'";
        int lenght = sql.length();
        if (sql.substring(lenght-5,lenght-1).trim().equals("and")) sql = sql.substring(0, lenght-5);
        List<Product> productList = getProduct(sql);
        return productList;
    }

    //Filter product by it's category
    //Đã tích hợp trong multiFilter
    public static List<Product> filterByCategory(String categoryId) throws Exception {
        String sql = "select * from dbo.Product where categoryId = '" + categoryId + "'";
        List<Product> productList = getProduct(sql);
        return productList;
    }

    //Filter product by price from x to y
    //Đã tích hợp trong multiFilter
    public static List<Product> sortByPrice(int from, int to) throws Exception {
        String sql = "Select * from dbo.Product where price >= " + from + " and price <= " + to;
        List<Product> productList = getProduct(sql);
        return productList;
    }

    //Filter product by status
    //Đã tích hợp trong multiFilter
    public static List<Product> filterByStatus(String status) throws Exception {
        String sql = "Select * from dbo.Product where status = N'" + status + "'";
        List<Product> productList = getProduct(sql);
        return productList;
    }

    //Get product quantity
    public static int getQuantity(int productId) throws Exception {
        String sql = "Select * from dbo.Product where productId = ?";
        int quantity = 0;
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, productId);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                table.next();
                quantity = table.getInt("quantity");
            }
        }
        return quantity;
    }

    //Update new quantity when customer purchase product
    public static ResponseEntity<String> updateQuantity(int buyQuantity, int productId) throws Exception {
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
                return ResponseEntity.ok().body("Update successful");
            }
        }
        return ResponseEntity.badRequest().body("Failed");
    }

    //Add new product
    public static ResponseEntity<String> createProduct(Product product) throws Exception {
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String dateCreate = date.format(now);
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "SET ANSI_WARNINGS OFF;" +
                    "INSERT INTO Product(productName, price, quantity, categoryId, status, description, image, dateCreate) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                    "SET ANSI_WARNINGS ON";
            /*String sql = "SET ANSI_WARNINGS OFF;" +
                    "INSERT INTO Product(productName, price, quantity, categoryId, status, description, image, dateCreate) " +
                    "VALUES (N'" + productName + "', " + price + ", " + quantity + ", " + categoryId + ", N'" + status + "', N'" + description + "', '" + image + "', '" + createDate + "') " +
                    "SET ANSI_WARNINGS ON;";*/

            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, product.getProductName());
            pst.setInt(2, product.getPrice());
            pst.setInt(3, product.getQuantity());
            pst.setInt(4, product.getCategoryId());
            pst.setString(5, product.getStatus());
            pst.setString(6, product.getDescription());
            pst.setString(7, product.getImage());
            pst.setString(8, dateCreate);

            int row = pst.executeUpdate();
            if (row > 0) {
                return ResponseEntity.ok().body("Create successful");
            }
        }
        return ResponseEntity.badRequest().body("Failed");
    }

    //Delete existing product by id
    public static ResponseEntity<String> deleteProduct(int productId) throws Exception {
        String sql = "Delete from dbo.Product where productId = ?";
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, productId);
            int row = pst.executeUpdate();
            if (row > 0) {
                return ResponseEntity.ok().body("Delete successful");
            }
        }
        return ResponseEntity.badRequest().body("Failed");
    }

    //Update existing product by id
    public static ResponseEntity<String> updateProduct(Product product) throws Exception {
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String dateUpdate = date.format(now);

        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "Update dbo.Product set productName = ?, price = ?, quantity = ?, categoryId = ?, status = ?, description = ?, image = ?, dateUpdate = ? where productId = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, product.getProductName());
            pst.setInt(2, product.getPrice());
            pst.setInt(3, product.getQuantity());
            pst.setInt(4, product.getCategoryId());
            pst.setString(5, product.getStatus());
            pst.setString(6, product.getDescription());
            pst.setString(7, product.getImage());
            pst.setString(8, dateUpdate);
            pst.setInt(9, product.getProductId());
            int row = pst.executeUpdate();
            if (row > 0) {
                return ResponseEntity.ok().body("Update successful");
            }
        }
        return ResponseEntity.badRequest().body("Failed");
    }
}
