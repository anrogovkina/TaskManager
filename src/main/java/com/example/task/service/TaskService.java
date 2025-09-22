package com.example.task.service;
import com.example.task.model.Task;
import java.util.List;



public interface TaskService {
    Task createTask(Task task);
    void deleteTask(Long taskId);
    List<Task> getAllTasks(Long userId);
    List<Task> getPendingTasks(Long userId);

}
