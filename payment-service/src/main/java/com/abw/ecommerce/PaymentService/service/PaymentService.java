package com.abw.ecommerce.PaymentService.service;

import com.abw.ecommerce.PaymentService.dto.PaymentDto;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PaymentService {
    Mono<List<PaymentDto>> findAll();
    Mono<Page<PaymentDto>> findAll(int page, int size, String sortBy, String sortOrder);
    Mono<PaymentDto> findById(Integer paymentId);
    Mono<PaymentDto> save(PaymentDto paymentDto);
    Mono<PaymentDto> update(PaymentDto paymentDto);
    Mono<PaymentDto> update(Integer paymentId, PaymentDto paymentDto);
    Mono<Void> deleteById(Integer paymentId);
}
