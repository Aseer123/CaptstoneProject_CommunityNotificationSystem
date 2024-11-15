package com.communityapp.feedback.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.communityapp.feedback.model.Feedback;
import com.communityapp.feedback.service.FeedbackService;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

	
	
	@Autowired
	private FeedbackService feedbackService;
	
	@GetMapping("/hello")
	public String hello() {
		return "Welcome again";
	}
	
	@PostMapping("/create")
	public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback){
		Feedback createdFeedback= feedbackService.createFeedback(feedback);
		return new ResponseEntity<>(createdFeedback,HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<Feedback>> getAllFeedback(){
		List<Feedback> feedbackList= feedbackService.getAllFeedback();
		return new ResponseEntity<>(feedbackList,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id){
		Feedback feedback= feedbackService.getFeedbackById(id);
		return new ResponseEntity<>(feedback,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Feedback> deleteFeedback(@PathVariable Long id){
		feedbackService.deleteFeedback(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Feedback> updateFeedback(@PathVariable Long id, @RequestBody Feedback feedbackDetails){
		Feedback updatedFeedback = feedbackService.updateFeedback(id, feedbackDetails);
		return new ResponseEntity<>(updatedFeedback,HttpStatus.OK);
	}
	
	@GetMapping("/role/{userRole}")
	public ResponseEntity<List<Feedback>> getFeedbackByUserRole(@PathVariable String userRole){
		List<Feedback> feedbackList=feedbackService.getFeedbackByUserRole(userRole);
		return new ResponseEntity<>(feedbackList,HttpStatus.OK);
	}
	
	
}
