package com.example.task.service.impl;

import com.example.task.model.Task;
import com.example.task.repository.TaskRepository;
import com.example.task.service.TaskService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("h2")
public class TaskServiceJpa implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceJpa(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.findById(taskId).ifPresent(task -> {task.setDeleted(true);
        taskRepository.save(task);});

    }

    @Override
    public List<Task> getAllTasks(Long userId) {
        return taskRepository.findAllByUserIdAndDeletedFalse(userId);
    }

    @Override
    public List<Task> getPendingTasks(Long userId) {
        return taskRepository.findAllByUserIdAndDeletedFalseAndCompletedFalse(userId);
    }
}
