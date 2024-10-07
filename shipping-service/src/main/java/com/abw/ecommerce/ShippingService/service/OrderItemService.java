package com.abw.ecommerce.ShippingService.service;

import com.abw.ecommerce.ShippingService.domain.id.OrderItemId;
import com.abw.ecommerce.ShippingService.dto.OrderItemDto;

import java.util.List;

public interface OrderItemService {

    List<OrderItemDto> findAll();
    OrderItemDto findById(final OrderItemId orderItemId);
    OrderItemDto save(final OrderItemDto orderItemDto);
    OrderItemDto update(final OrderItemDto orderItemDto);
    void deleteById(final OrderItemId orderItemId);

}