package com.communityapp.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.communityapp.notification.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{
	List<Notification> findByRecipientRole(String recipientRole);
}
