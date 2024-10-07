package com.abw.ecommerce.NotificationService.repository;

import com.abw.ecommerce.NotificationService.entity.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, Integer> {

}
