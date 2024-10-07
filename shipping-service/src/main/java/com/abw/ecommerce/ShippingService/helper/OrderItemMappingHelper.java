package com.abw.ecommerce.ShippingService.helper;

import com.abw.ecommerce.ShippingService.domain.OrderItem;
import com.abw.ecommerce.ShippingService.dto.OrderDto;
import com.abw.ecommerce.ShippingService.dto.OrderItemDto;
import com.abw.ecommerce.ShippingService.dto.ProductDto;

public interface OrderItemMappingHelper {

    static OrderItemDto map(final OrderItem orderItem) {
        return OrderItemDto.builder()
                .productId(orderItem.getProductId())
                .orderId(orderItem.getOrderId())
                .orderedQuantity(orderItem.getOrderedQuantity())
                .productDto(
                        ProductDto.builder()
                                .productId(orderItem.getProductId())
                                .build())
                .orderDto(
                        OrderDto.builder()
                                .orderId(orderItem.getOrderId())
                                .build())
                .build();
    }

    static OrderItem map(final OrderItemDto orderItemDto) {
        return OrderItem.builder()
                .productId(orderItemDto.getProductId())
                .orderId(orderItemDto.getOrderId())
                .orderedQuantity(orderItemDto.getOrderedQuantity())
                .build();
    }

}