package com.example.task.repository;

import com.example.task.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUserId(Long userId);
    List<Notification> findAllByUserIdAndReadFalse(Long userId);
}
