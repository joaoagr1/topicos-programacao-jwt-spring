package com.example.api.repositories;

import com.example.api.entitities.Task;
import com.example.api.enums.TaskStatus;
import com.example.api.entitities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
    List<Task> findByUserAndStatus(User user, TaskStatus status);
} 