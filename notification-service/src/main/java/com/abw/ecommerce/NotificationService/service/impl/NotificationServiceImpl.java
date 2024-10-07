package com.abw.ecommerce.NotificationService.service.impl;

import com.abw.ecommerce.NotificationService.entity.Notification;
import com.abw.ecommerce.NotificationService.repository.NotificationRepository;
import com.abw.ecommerce.NotificationService.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Optional<Notification> getNotificationById(String id) {
        return notificationRepository.findById(id);
    }

    @Override
    public Notification saveNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public void deleteNotificationById(String id) {
        notificationRepository.deleteById(id);
    }

}
