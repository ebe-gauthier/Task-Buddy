package com.taskbuddy.taskbuddyapi.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Task model — represents a single task document in MongoDB.
 *
 * @Document maps this class to the "tasks" collection.
 * @Data (Lombok) auto-generates getters, setters, toString, equals, hashCode.
 */
@Document(collection = "tasks")
@Data
public class Task {

    /** MongoDB auto-generated unique ID */
    @Id
    private String id;

    /** Owner of this task (User ID) */
    private String userId;

    /** Short title of the task */
    private String title;

    /** Longer description explaining the task */
    private String description;

    /**
     * Priority level of the task.
     * Accepted values: LOW, MEDIUM, HIGH
     */
    private String priority;

    /**
     * Current status of the task.
     * Accepted values: TODO, IN_PROGRESS, DONE
     */
    private String status;
}
