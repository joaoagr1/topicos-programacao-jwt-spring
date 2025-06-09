package com.example.api.controller;

import com.example.api.dto.TaskRequestDTO;
import com.example.api.dto.TaskResponseDTO;
import com.example.api.entitities.User;
import com.example.api.enums.TaskStatus;
import com.example.api.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(
            @RequestBody TaskRequestDTO taskDTO,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        TaskResponseDTO createdTask = taskService.createTask(taskDTO, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<TaskResponseDTO> tasks = taskService.getAllTasks(user);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByStatus(
            @PathVariable TaskStatus status,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<TaskResponseDTO> tasks = taskService.getTasksByStatus(user, status);
        return ResponseEntity.ok(tasks);
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<TaskResponseDTO> updateTaskStatus(
            @PathVariable Long taskId,
            @RequestParam TaskStatus status,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        TaskResponseDTO updatedTask = taskService.updateTaskStatus(taskId, status, user);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long taskId,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        taskService.deleteTask(taskId, user);
        return ResponseEntity.noContent().build();
    }
} 