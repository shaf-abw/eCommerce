package com.abw.ecommerce.OrderService.service;

import com.abw.ecommerce.OrderService.dto.product.ProductDto;
import com.abw.ecommerce.OrderService.dto.user.UserDto;
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

    public Mono<UserDto> receiverUserDto(Long userId, String token) {
        return webClientBuilder.baseUrl("http://localhost:8081").build()
                .get()
                .uri("/api/manager/user/" + userId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(UserDto.class);
    }

    public Mono<ProductDto> receiverProductDto(Integer productId) {
        return webClientBuilder.baseUrl("http://localhost:8082").build()
                .get()
                .uri("/api/products/" + productId)
                .retrieve()
                .bodyToMono(ProductDto.class);
    }

}
