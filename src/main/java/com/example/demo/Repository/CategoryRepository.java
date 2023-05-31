package com.example.demo.Repository;

import com.example.demo.DBConnection.DBUtils;
import com.example.demo.model.Category;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;

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

    //Get all category
    public static Category getCategoryById(int categoryId) throws Exception {
        Category category = new Category();
        Connection cn = DBUtils.makeConnection();
        if (cn!= null) {
            String sql = "select * from dbo.Category where categoryId = ? ";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1,categoryId);
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
}


