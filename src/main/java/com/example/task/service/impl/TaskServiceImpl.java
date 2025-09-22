package com.example.task.service.impl;

import com.example.task.model.Task;
import com.example.task.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final Map<Long, Task> tasks = new HashMap<>();

    @Override
    public Task createTask(Task task) {
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public void deleteTask(Long taskId) {
        Task task = tasks.get(taskId);
        if (task != null) {
            task.setDeleted(true);
        }
    }

    @Override
    public List<Task> getAllTasks(Long userId) {
        return tasks.values().stream()
                .filter(t -> t.getUserId().equals(userId) && !t.isDeleted())
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getPendingTasks(Long userId) {
        return tasks.values().stream()
                .filter(t -> t.getUserId().equals(userId) && !t.isDeleted() && !t.isCompleted())
                .collect(Collectors.toList());
    }

}