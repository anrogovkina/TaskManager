package com.example.task.service;
import com.example.task.model.Notification;
import java.util.List;



public interface NotificationService {

    Notification createNotification(Notification notification);
    List<Notification> getAllNotifications(Long userId);
    List<Notification> getPendingNotifications(Long userId);

}

