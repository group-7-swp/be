package com.example.demo.Repository;

import com.example.demo.DBConnection.DBUtils;
import com.example.demo.model.Category;
import com.example.demo.model.CategoryAndProduct;
import com.example.demo.model.Product;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    public static List<Category> getAllCategory() throws Exception {
        List<Category> categoryList = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null){
            String sql = "Select * from dbo.Category";
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    Category category = new Category();
                    category.setCategoryId(table.getInt("categoryId"));
                    category.setCategoryName(table.getString("categoryName"));
                    categoryList.add(category);
                }
            }
        } return categoryList;
    }

    public static Category getCategoryById(int categoryId) throws Exception {
        Category category = new Category();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "Select * from dbo.Category where categoryId = ? ";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, categoryId);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    category.setCategoryId(table.getInt("categoryId"));
                    category.setCategoryName(table.getString("categoryName"));
                }
            }
        }
        return category;
    }

    //Create new Category
    public static Category createCategory(String categoryName) throws Exception {
        Category category = new Category();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "INSERT INTO dbo.Category (categoryName) VALUES (?)";
            PreparedStatement pst = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, categoryName);
            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = pst.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int categoryId = generatedKeys.getInt(1);
                    category.setCategoryId(categoryId);
                    category.setCategoryName(categoryName);
                }
            }
        } return category;
    }

    //Update Category
    public static ResponseEntity<String> updateCategory(Category category) throws Exception {
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "Update dbo.Category Set categoryName = ? WHERE categoryId = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, category.getCategoryName());
            pst.setInt(2, category.getCategoryId());
            int row = pst.executeUpdate();
            if (row > 0) {
                return ResponseEntity.ok().body("Update Successfully");
            }
        }
                return ResponseEntity.badRequest().body("Update Fail");
    }

    //Delete Category
    public static ResponseEntity<String> deleteCategory(int[] categoryId) throws Exception {
        Connection cn = DBUtils.makeConnection();
        int count = 0;
        if (cn!= null) {
            for (int i = 0; i<categoryId.length; i++) {
                String sql = "Delete from dbo.Category where categoryId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, categoryId[i]);
                int row = pst.executeUpdate();
                if(row > 0) count++;
            }
            if (count > 0) return ResponseEntity.ok().body("Delete Successfully");
        }
        return ResponseEntity.badRequest().body("Delete Fail");
    }


    //Get both Category and Product
    public static List<CategoryAndProduct> getCategoryAndProduct() throws Exception {
        List<CategoryAndProduct> categoryAndProductList = new ArrayList<>();
        List<Category> categoryList = getAllCategory();

        for (int i = 0; i < categoryList.size(); i++){
            int categoryId = categoryList.get(i).getCategoryId();
            String categoryName = categoryList.get(i).getCategoryName();
            String sql = "select * from dbo.Product where categoryId = '" + categoryId + "'";
            List<Product> productList= new ArrayList<>();

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
            CategoryAndProduct categoryAndProduct = new CategoryAndProduct();
            categoryAndProduct.setCategoryId(categoryId);
            categoryAndProduct.setCategoryName(categoryName);
            categoryAndProduct.setProductList(productList);
            categoryAndProductList.add(categoryAndProduct);
        }
        return categoryAndProductList;
    }
}
