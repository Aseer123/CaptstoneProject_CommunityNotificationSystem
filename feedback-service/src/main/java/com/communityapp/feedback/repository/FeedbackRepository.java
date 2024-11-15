package com.communityapp.feedback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.communityapp.feedback.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long>{

}
