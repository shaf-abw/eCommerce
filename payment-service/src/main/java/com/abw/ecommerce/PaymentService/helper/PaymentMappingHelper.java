package com.abw.ecommerce.PaymentService.helper;

import com.abw.ecommerce.PaymentService.dto.OrderDto;
import com.abw.ecommerce.PaymentService.dto.PaymentDto;
import com.abw.ecommerce.PaymentService.dto.UserDto;
import com.abw.ecommerce.PaymentService.entity.Payment;

public interface PaymentMappingHelper {
    static PaymentDto map(final Payment payment) {
        return PaymentDto.builder()
                .paymentId(payment.getPaymentId())
                .isPayed(payment.getIsPayed())
                .paymentStatus(payment.getPaymentStatus())
                .orderId(payment.getOrderId())
                .userId(payment.getUserId())
                .orderDto(
                        OrderDto.builder()
                                .orderId(payment.getOrderId())
                                .build())
                .userDto(UserDto.builder()
                        .id(payment.getUserId())
                        .build())
                .build();
    }

    static Payment map(final PaymentDto paymentDto) {
        return Payment.builder()
                .paymentId(paymentDto.getPaymentId())
                .orderId(paymentDto.getOrderId())
                .userId(paymentDto.getUserId())
                .isPayed(paymentDto.getIsPayed())
                .paymentStatus(paymentDto.getPaymentStatus())
                .build();
    }
}
