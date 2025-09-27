package com.example.task.service.impl;

import com.example.task.model.Notification;
import com.example.task.repository.NotificationRepository;
import com.example.task.service.NotificationService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile({"h2","postgres"})
public class NotificationServiceJpa implements NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationServiceJpa(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    @Override
    @CacheEvict(value = { "notifications", "pendingNotifications" }, key = "#notification.userId")
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    @Cacheable(value = "notifications", key = "#userId")
    public List<Notification> getAllNotifications(Long userId) {
        return notificationRepository.findAllByUserId(userId);
    }

    @Override
    @Cacheable(value = "pendingNotifications", key = "#userId")
    public List<Notification> getPendingNotifications(Long userId) {
        return notificationRepository.findAllByUserIdAndReadFalse(userId);
    }
}
