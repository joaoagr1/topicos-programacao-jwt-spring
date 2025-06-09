package com.example.api.exception;

public class TaskException extends RuntimeException {
    public TaskException(String message) {
        super(message);
    }

    public static class TaskNotFoundException extends TaskException {
        public TaskNotFoundException(Long id) {
            super("Tarefa não encontrada com ID: " + id);
        }
    }

    public static class UnauthorizedTaskAccessException extends TaskException {
        public UnauthorizedTaskAccessException(Long taskId) {
            super("Você não tem permissão para acessar a tarefa com ID: " + taskId);
        }
    }
} 