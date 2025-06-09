package com.example.api.services;

import com.example.api.dto.TaskRequestDTO;
import com.example.api.dto.TaskResponseDTO;
import com.example.api.entitities.Task;
import com.example.api.entitities.User;
import com.example.api.enums.TaskStatus;
import com.example.api.exception.TaskException;
import com.example.api.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponseDTO createTask(TaskRequestDTO taskDTO, User user) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(TaskStatus.PENDING);
        task.setUser(user);
        
        Task savedTask = taskRepository.save(task);
        return convertToResponseDTO(savedTask);
    }

    public List<TaskResponseDTO> getAllTasks(User user) {
        return taskRepository.findByUser(user).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<TaskResponseDTO> getTasksByStatus(User user, TaskStatus status) {
        return taskRepository.findByUserAndStatus(user, status).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public TaskResponseDTO updateTaskStatus(Long taskId, TaskStatus newStatus, User user) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskException.TaskNotFoundException(taskId));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new TaskException.UnauthorizedTaskAccessException(taskId);
        }

        task.setStatus(newStatus);
        Task updatedTask = taskRepository.save(task);
        return convertToResponseDTO(updatedTask);
    }

    public void deleteTask(Long taskId, User user) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskException.TaskNotFoundException(taskId));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new TaskException.UnauthorizedTaskAccessException(taskId);
        }

        taskRepository.delete(task);
    }

    private TaskResponseDTO convertToResponseDTO(Task task) {
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setUserName(task.getUser().getEmail());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());
        return dto;
    }
} 