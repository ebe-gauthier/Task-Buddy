package com.taskbuddy.taskbuddyapi.repository;

import com.taskbuddy.taskbuddyapi.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByUserId(String userId);
    List<Task> findByUserIdAndStatus(String userId, String status);
    List<Task> findByUserIdAndPriority(String userId, String priority);
}
