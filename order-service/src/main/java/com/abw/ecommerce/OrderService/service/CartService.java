package com.abw.ecommerce.OrderService.service;

import com.abw.ecommerce.OrderService.dto.order.CartDto;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CartService {
    Mono<List<CartDto>> findAll();

    Mono<Page<CartDto>> findAll(int page, int size, String sortBy, String sortOrder);

    Mono<CartDto> findById(Integer cartId);

    Mono<CartDto> save(final CartDto cartDto);

    Mono<CartDto> update(final CartDto cartDto);

    Mono<CartDto> update(final Integer cartId, final CartDto cartDto);

    Mono<Void> deleteById(final Integer cartId);
}
