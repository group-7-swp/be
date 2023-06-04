package com.example.demo.Repository;

import com.example.demo.DBConnection.DBUtils;
import com.example.demo.model.Feedback;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FeedbackRepository {
    //Get feedback by input sql string
    public static List<Feedback> getFeedback(String sql) throws Exception {
        List<Feedback> feedbackList = new ArrayList<>();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    Feedback feedback = new Feedback();
                    feedback.setFeedbackId(table.getInt("feedbackId"));
                    feedback.setUserId(table.getInt("userId"));
                    feedback.setProductId(table.getInt("productId"));
                    feedback.setContent(table.getString("content"));
                    feedback.setDate(table.getDate("date"));
                    feedbackList.add(feedback);
                }
            }
        }
        return feedbackList;
    }

    //Get all feedback
    public static List<Feedback> getAllFeedback() throws Exception {
        List<Feedback> feedbackList = new ArrayList<>();
        String sql = "select * from dbo.Feedback";
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    Feedback feedback = new Feedback();
                    feedback.setFeedbackId(table.getInt("feedbackId"));
                    feedback.setUserId(table.getInt("userId"));
                    feedback.setProductId(table.getInt("productId"));
                    feedback.setContent(table.getString("content"));
                    feedback.setDate(table.getDate("date"));
                    feedbackList.add(feedback);
                }
            }
        }
        return feedbackList;
    }

    //Get feedback by id
    public static Feedback getFeedbackById(int feedbackId) throws Exception {
        Feedback feedback = new Feedback();
        String sql = "select * from dbo.Feedback where feedbackId = ?";
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, feedbackId);
            ResultSet table = pst.executeQuery();
            if (table != null) {
                while (table.next()) {
                    feedback.setFeedbackId(table.getInt("feedbackId"));
                    feedback.setUserId(table.getInt("userId"));
                    feedback.setProductId(table.getInt("productId"));
                    feedback.setContent(table.getString("content"));
                    feedback.setDate(table.getDate("date"));
                }
            }
        }
        return feedback;
    }

    //Add new feedback
    public static ResponseEntity<String> createFeedback(Feedback feedback) throws Exception {
        String date = DBUtils.getCurrentDate();
        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "SET ANSI_WARNINGS OFF;" +
                    "INSERT INTO Feedback(userId, productId, content, date)" +
                    "VALUES (?, ?, ?, ?) " +
                    "SET ANSI_WARNINGS ON";

            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, feedback.getUserId());
            pst.setInt(2, feedback.getProductId());
            pst.setString(3, feedback.getContent());
            pst.setString(4, date);

            int row = pst.executeUpdate();
            if (row > 0) {
                return ResponseEntity.ok().body("Create successful");
            }
        }
        return ResponseEntity.badRequest().body("Failed");
    }

    //Delete existing feedback by id
    public static ResponseEntity<String> deleteFeedback(int[] feedbackId) throws Exception {
        String sql = "Delete from dbo.Feedback where feedbackId = ?";
        Connection cn = DBUtils.makeConnection();
        int count = 0;
        if (cn != null) {
            for (int i = 0; i<feedbackId.length; i++) {
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, feedbackId[i]);
                int row = pst.executeUpdate();
                if (row > 0) count++;
            }
            if (count > 0) return ResponseEntity.ok().body("Delete successful");
        }
        return ResponseEntity.badRequest().body("Failed");
    }

    //Update existing feedback by id
    public static ResponseEntity<String> updateFeedback(Feedback feedback) throws Exception {
        String dateUpdate = DBUtils.getCurrentDate();

        Connection cn = DBUtils.makeConnection();
        if (cn != null) {
            String sql = "Update dbo.Feedback set content = ? where feedbackId = ?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, feedback.getContent());
            pst.setInt(2, feedback.getFeedbackId());
            int row = pst.executeUpdate();
            if (row > 0) {
                return ResponseEntity.ok().body("Update successful");
            }
        }
        return ResponseEntity.badRequest().body("Failed");
    }
}
