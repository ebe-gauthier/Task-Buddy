package com.taskbuddy.taskbuddyapi.controller;

import com.taskbuddy.taskbuddyapi.model.Task;
import com.taskbuddy.taskbuddyapi.model.User;
import com.taskbuddy.taskbuddyapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService service;

    @GetMapping
    public ResponseEntity<List<Task>> getTasks(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority) {

        if (status != null) return ResponseEntity.ok(service.getTasksByUserAndStatus(user.getId(), status));
        if (priority != null) return ResponseEntity.ok(service.getTasksByUserAndPriority(user.getId(), priority));
        return ResponseEntity.ok(service.getTasksByUser(user.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id) {
        return service.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task,
                                           @AuthenticationPrincipal User user) {
        task.setUserId(user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createTask(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable String id,
                                           @RequestBody Task task,
                                           @AuthenticationPrincipal User user) {
        return service.updateTask(id, task, user.getId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id,
                                           @AuthenticationPrincipal User user) {
        return service.deleteTask(id, user.getId())
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
