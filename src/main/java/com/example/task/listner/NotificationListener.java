package com.example.task.listner;

import com.example.task.config.RabbitConfig;
import com.example.task.model.Task;
import com.example.task.model.Notification;
import com.example.task.repository.NotificationRepository;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {
    private final NotificationRepository notificationRepository;

    public NotificationListener(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @RabbitListener(queues = RabbitConfig.TASK_QUEUE)
    public void notificationCreator(Task task) {
        try {
            Notification notification = new Notification();
            notification.setUserId(task.getUserId());
            notification.setMessage("Task " + task.getTitle() + " created");
            notification.setRead(false);
            notificationRepository.save(notification);
        }catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException("Processing error",e);
        }
    }
}
