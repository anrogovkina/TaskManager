package com.example.task.repository;

import com.example.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUserIdAndDeletedFalse(Long userId);
    List<Task> findAllByUserIdAndDeletedFalseAndCompletedFalse(Long userId);
}
