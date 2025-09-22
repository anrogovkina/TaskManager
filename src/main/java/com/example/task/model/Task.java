package com.example.task.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private Long id;
    private String title;
    private String description;
    private Long userId;
    private LocalDateTime creationDate = LocalDateTime.now();
    private LocalDateTime targetDate;
    private boolean completed = false;
    private boolean deleted = false;
}