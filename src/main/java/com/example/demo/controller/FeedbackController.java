package com.example.demo.controller;

import com.example.demo.Repository.FeedbackRepository;
import com.example.demo.model.Feedback;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
    @GetMapping("/allFeedback")
    public ResponseEntity<Object> getAllFeedback() throws Exception {
        List<Feedback> feedbackList = FeedbackRepository.getAllFeedback();
        if(feedbackList.size()>0) return ResponseEntity.ok().body(feedbackList);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getFeedbackById")
    public ResponseEntity<Object> getFeedbackById(@RequestParam int feedbackId) throws Exception {
        Feedback feedback = FeedbackRepository.getFeedbackById(feedbackId);
        if(feedback!=null) return ResponseEntity.ok().body(feedback);
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/createFeedback")
    public ResponseEntity<String> createFeedback(@RequestBody Feedback feedback) throws Exception {
        return FeedbackRepository.createFeedback(feedback);
    }

    @DeleteMapping("/deleteFeedback")
    public ResponseEntity<String> deleteFeedback(@RequestParam int[] feedbackId) throws Exception {
        return FeedbackRepository.deleteFeedback(feedbackId);
    }

    @PatchMapping("/updateFeedback")
    public ResponseEntity<String> updateFeedback(@RequestBody Feedback feedback) throws Exception {
        return FeedbackRepository.updateFeedback(feedback);
    }
}
