package com.example.demo.Repository;

import com.example.demo.DBConnection.DBUtils;
import com.example.demo.model.Category;

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
                }}
            } return categoryList;
        }
    }
