package com.abw.ecommerce.NotificationService.service;

import com.abw.ecommerce.NotificationService.dto.PaymentDto;
import com.abw.ecommerce.NotificationService.entity.Payment;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PaymentService {
    Mono<Payment> savePayment(PaymentDto paymentDto);
    Mono<Payment> getPayment(Integer paymentId);
    Mono<List<Payment>> getAllPayments();
    Mono<Void> deletePayment(Integer paymentId);
}
