package com.example.api.dto;

import com.example.api.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private String userName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 