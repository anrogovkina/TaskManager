package com.example.task.service.impl;

import com.example.task.model.Notification;
import com.example.task.repository.NotificationRepository;
import com.example.task.service.NotificationService;
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
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getAllNotifications(Long userId) {
        return notificationRepository.findAllByUserId(userId);
    }

    @Override
    public List<Notification> getPendingNotifications(Long userId) {
        return notificationRepository.findAllByUserIdAndReadFalse(userId);
    }
}
