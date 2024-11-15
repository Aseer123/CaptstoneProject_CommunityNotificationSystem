package com.communityapp.notification.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.communityapp.notification.model.Notification;
import com.communityapp.notification.service.NotificationService;

@RestController
@RequestMapping("/notifications")
@CrossOrigin
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/hello")
    public String hello() {
        return "Welcome back";
    }

    // Endpoint for creating a notification
    @PostMapping
    public ResponseEntity<?> createNotification(@RequestBody Notification notification) {
        try {
            // Validate notification content
            if (notification.getContent() == null || notification.getContent().trim().isEmpty()) {
                return new ResponseEntity<>("Notification content cannot be empty", 
                    HttpStatus.BAD_REQUEST);
            }
            
            // Validate recipient role
            if (notification.getRecipientRole() == null || notification.getRecipientRole().trim().isEmpty()) {
                return new ResponseEntity<>("Recipient role cannot be empty", 
                    HttpStatus.BAD_REQUEST);
            }

            Notification createdNotification = notificationService.createNotification(notification);
            return new ResponseEntity<>(createdNotification, HttpStatus.CREATED);
            
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create notification: " + e.getMessage(), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint for getting notifications based on user role
    @GetMapping("/{role}")
    public ResponseEntity<?> getNotifications(@PathVariable String role) {
        try {
            // Validate role
            if (role == null || role.trim().isEmpty()) {
                return new ResponseEntity<>("Role cannot be empty", 
                    HttpStatus.BAD_REQUEST);
            }

            List<Notification> notifications = notificationService.getNotificationsByRole(role);
            
            if (notifications.isEmpty()) {
                return new ResponseEntity<>(notifications, HttpStatus.OK); // Return empty list instead of 404
            }
            
            return new ResponseEntity<>(notifications, HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch notifications: " + e.getMessage(), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}