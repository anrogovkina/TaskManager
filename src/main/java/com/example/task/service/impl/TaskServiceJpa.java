package com.example.task.service.impl;

import com.example.task.config.RabbitConfig;
import com.example.task.model.Task;
import com.example.task.repository.TaskRepository;
import com.example.task.service.TaskService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile({"h2","postgres"})
public class TaskServiceJpa implements TaskService {

    private final TaskRepository taskRepository;
    private final RabbitTemplate rabbitTemplate;
    public TaskServiceJpa(TaskRepository taskRepository, RabbitTemplate rabbitTemplate) {
        this.taskRepository = taskRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    @CacheEvict(value = { "tasks", "pendingTasks" }, key = "#task.userId")
    public Task createTask(Task task) {
        Task created = taskRepository.save(task);

        rabbitTemplate.convertAndSend(
                RabbitConfig.TASK_EXCHANGE,
                RabbitConfig.TASK_ROUTING_KEY,
                created
        );
        return created;
    }

    @Override
    @CacheEvict(value = { "tasks", "pendingTasks" }, allEntries = true)
    public void deleteTask(Long taskId) {
        taskRepository.findById(taskId).ifPresent(task -> {task.setDeleted(true);
        taskRepository.save(task);});

    }

    @Override
    @Cacheable(value = "tasks", key = "#userId")
    public List<Task> getAllTasks(Long userId) {
        return taskRepository.findAllByUserIdAndDeletedFalse(userId);
    }

    @Override
    @Cacheable(value = "pendingTasks", key = "#userId")
    public List<Task> getPendingTasks(Long userId) {
        return taskRepository.findAllByUserIdAndDeletedFalseAndCompletedFalse(userId);
    }
}
