package com.oristo.taskManagement.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tasks")
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(columnDefinition = "TEXT")
  private String description;

  private LocalDate dueDate;

  @Column(nullable = false)
  private String status;

  @Column(columnDefinition = "TEXT")
  private String remarks;

  @Column(nullable = false)
  private LocalDateTime createdOn;

  private LocalDateTime lastUpdatedOn;

  @ManyToOne
  @JoinColumn(name = "created_by", nullable = false)
  private User createdBy;

  @ManyToOne
  @JoinColumn(name = "last_updated_by")
  private User lastUpdatedBy;
}