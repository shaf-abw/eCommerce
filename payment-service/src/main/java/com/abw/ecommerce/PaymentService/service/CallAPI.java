package com.abw.ecommerce.PaymentService.service;

import com.abw.ecommerce.PaymentService.dto.OrderDto;
import com.abw.ecommerce.PaymentService.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CallAPI {
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public CallAPI(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<OrderDto> receiverPaymentDto(Integer orderId, String token) {
        return webClientBuilder.baseUrl("http://localhost:8083").build()
                .get()
                .uri("/api/orders/" + orderId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(OrderDto.class);
    }

    public Mono<UserDto> receiverUserDto(Long userId, String token) {
        return webClientBuilder.baseUrl("http://localhost:8081").build()
                .get()
                .uri("/api/manager/user/" + userId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(UserDto.class);
    }

}
