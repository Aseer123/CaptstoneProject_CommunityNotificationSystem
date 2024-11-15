package com.communityapp.notification.model;

import java.util.Date;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;
    
    @Column(nullable = false)
    private Date date;  // Changed from timestamp to date
    
    @Column(nullable = false)
    private String recipientRole;
    
    private String recipientEmail;
    
    private String subject;
    
    @PrePersist
    protected void onCreate() {
        date = new Date();  // Set current date when creating notification
    }
}