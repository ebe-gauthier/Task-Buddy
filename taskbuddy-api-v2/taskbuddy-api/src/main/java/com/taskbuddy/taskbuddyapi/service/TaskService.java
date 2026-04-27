package com.taskbuddy.taskbuddyapi.service;

import com.taskbuddy.taskbuddyapi.model.Task;
import com.taskbuddy.taskbuddyapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public List<Task> getTasksByUser(String userId) {
        return repository.findByUserId(userId);
    }

    public List<Task> getTasksByUserAndStatus(String userId, String status) {
        return repository.findByUserIdAndStatus(userId, status);
    }

    public List<Task> getTasksByUserAndPriority(String userId, String priority) {
        return repository.findByUserIdAndPriority(userId, priority);
    }

    public Optional<Task> getTaskById(String id) {
        return repository.findById(id);
    }

    public Task createTask(Task task) {
        return repository.save(task);
    }

    public Optional<Task> updateTask(String id, Task updatedTask, String userId) {
        return repository.findById(id)
                .filter(t -> t.getUserId().equals(userId))
                .map(existing -> {
                    existing.setTitle(updatedTask.getTitle());
                    existing.setDescription(updatedTask.getDescription());
                    existing.setPriority(updatedTask.getPriority());
                    existing.setStatus(updatedTask.getStatus());
                    return repository.save(existing);
                });
    }

    public boolean deleteTask(String id, String userId) {
        return repository.findById(id)
                .filter(t -> t.getUserId().equals(userId))
                .map(t -> { repository.delete(t); return true; })
                .orElse(false);
    }
}
