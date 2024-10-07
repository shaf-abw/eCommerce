package com.abw.ecommerce.NotificationService.repository;

import com.abw.ecommerce.NotificationService.entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

}
