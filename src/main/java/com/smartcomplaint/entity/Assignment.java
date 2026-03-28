package com.smartcomplaint.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Complaint complaint;

    @ManyToOne
    private User officer;

    @CreationTimestamp
    private LocalDateTime assignedAt;

    private LocalDateTime resolvedAt;

    // Getters and Setters
}

