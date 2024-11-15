package com.communityapp.feedback.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.communityapp.feedback.model.Feedback;
import com.communityapp.feedback.repository.FeedbackRepository;

@Service
public class FeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepository;
	
	
	public Feedback createFeedback(Feedback feedback) {
		feedback.setCreatedAt(new Date()); // set current date
		return feedbackRepository.save(feedback);
	}
	
	public List<Feedback> getAllFeedback(){
		return feedbackRepository.findAll();
	}
	
	public Feedback getFeedbackById(Long id) {
		return feedbackRepository.findById(id).orElseThrow(()-> new RuntimeException("Feedback Not Found"));
	}
	
	public void deleteFeedback(Long feedbackId) {
		feedbackRepository.deleteById(feedbackId);
	}
	
	public Feedback updateFeedback(Long id, Feedback feedbackDetails) {
		Feedback existingFeedback= feedbackRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Feedback Not Found"));
		existingFeedback.setMessage(feedbackDetails.getMessage());
		existingFeedback.setUserRole(feedbackDetails.getUserRole());
		existingFeedback.setStatus(feedbackDetails.getStatus());
		return feedbackRepository.save(existingFeedback);
	}
	
	
	public List<Feedback> getFeedbackByUserRole(String userRole){
		return feedbackRepository.findAll().stream().filter(feedback-> feedback.getUserRole().equalsIgnoreCase(userRole)).toList();
	}
}
