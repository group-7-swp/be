package com.example.demo.Repository;

import com.example.demo.DBConnection.DBUtils;
import com.example.demo.model.Category;
import com.example.demo.model.CategoryAndProduct;
import com.example.demo.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    public static List<Category> getAllCategory() throws Exception {
        List<Category> categoryList = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null){
            String sql = "select * from dbo.Category";
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
                        product.setCreateDate(table.getDate("createDate"));
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
