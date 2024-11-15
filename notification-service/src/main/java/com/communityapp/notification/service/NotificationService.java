package com.communityapp.notification.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.communityapp.notification.model.Notification;
import com.communityapp.notification.repository.NotificationRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class NotificationService {
	private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private SMSService smsService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserService userService;

	@Value("${admin.phone.number}") // Add this to your application.properties
	private String adminPhoneNumber; // This will be your phone number

	public Notification createNotification(Notification notification) {
		try {
			// Save notification to database
			Notification savedNotification = notificationRepository.save(notification);

			// If notification is for residents, send to all residents
			if ("RESIDENT".equalsIgnoreCase(notification.getRecipientRole())) {
				// Send emails to all residents
				sendToAllResidents(notification);

				// Send SMS only to admin number
				sendSMSToAdmin(notification);
			}

			return savedNotification;
		} catch (Exception e) {
			logger.error("Error creating notification: ", e);
			throw new RuntimeException("Failed to create notification: " + e.getMessage());
		}
	}

	private void sendSMSToAdmin(Notification notification) {
		try {
			Twilio.init("ACdcac6300035a9917a82d3bf24cc6fbb1", "60b975c6e3b8a2b0c9a8ace954841864");

			Message message = Message.creator(new PhoneNumber("+917842808053"), new PhoneNumber("+18599558860"),
					notification.getContent()).create();
		} catch (Exception e) {
			logger.error("Error sending SMS to admin: ", e);
			System.err.println("Failed to send SMS to admin: " + e.getMessage());
		}
	}

	private String formatSMSContent(Notification notification) {
		return String.format("Community Notification:\n%s\nTime: %s", notification.getContent(),
				notification.getDate().toString());
	}

	private void sendToAllResidents(Notification notification) {
		try {
			// Get all resident emails
			List<String> residentEmails = userService.getAllResidentEmails();
			logger.info("Found {} resident emails to notify", residentEmails.size());

			// Send emails asynchronously to all residents
			residentEmails.forEach(email -> {
				CompletableFuture.runAsync(() -> {
					try {
						emailService.sendEmail(email, "Community Notification", formatEmailContent(notification));
						logger.info("Email sent successfully to: {}", email);
					} catch (Exception e) {
						logger.error("Failed to send email to {}: {}", email, e.getMessage());
					}
				});
			});
		} catch (Exception e) {
			logger.error("Error sending notifications to residents: ", e);
			System.err.println("Error sending notifications to residents: " + e.getMessage());
		}
	}

	private String formatEmailContent(Notification notification) {
		return String.format("""
				Dear Resident,

				%s

				Time: %s

				Best regards,
				Community Management Team
				""", notification.getContent(), notification.getDate().toString());
	}

	public List<Notification> getNotificationsByRole(String role) {
		return notificationRepository.findByRecipientRole(role);
	}

	public List<Notification> getAllNotifications() {
		return notificationRepository.findAll();
	}
}