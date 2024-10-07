package com.abw.ecommerce.PaymentService.dto;

import com.abw.ecommerce.PaymentService.entity.PaymentStatus;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
@Builder
public class KafkaPaymentDto {
    private Integer paymentId;
    private Boolean isPayed;
    private PaymentStatus paymentStatus;
    private Integer orderId;
    private Long userId;
}
