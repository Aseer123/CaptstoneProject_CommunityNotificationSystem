package com.communityapp.maintenancetask.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="tasks")
public class Task {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long taskId;

    @Column(nullable=false)
    private String description;

    @Column(nullable=false)
    private String status;

    @Column(nullable=false)
    private Date scheduledDate;

    @Column(nullable=false)
    private String creatorRole; // e.g., Admin
}
