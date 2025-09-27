package com.example.task.repository;

import com.example.task.model.Task;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUserIdAndDeletedFalse(Long userId);
    List<Task> findAllByUserIdAndDeletedFalseAndCompletedFalse(Long userId);
    List<Task> findByTargetDateBeforeAndCompletedFalseAndDeletedFalse(LocalDateTime date);
    @Query("SELECT t FROM Task t WHERE t.targetDate BETWEEN :startOfDay AND :endOfDay AND t.completed = false AND t.deleted = false")
    List<Task> findTasksDueToday(@Param("startOfDay") LocalDateTime startOfDay,
                                 @Param("endOfDay") LocalDateTime endOfDay);
}

