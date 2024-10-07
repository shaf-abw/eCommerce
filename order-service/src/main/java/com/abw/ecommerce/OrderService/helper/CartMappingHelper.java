package com.abw.ecommerce.OrderService.helper;

import com.abw.ecommerce.OrderService.dto.order.CartDto;
import com.abw.ecommerce.OrderService.dto.order.OrderDto;
import com.abw.ecommerce.OrderService.dto.user.UserDto;
import com.abw.ecommerce.OrderService.entity.Cart;
import com.abw.ecommerce.OrderService.entity.Order;

import java.util.stream.Collectors;

public interface CartMappingHelper {
    static CartDto map( Cart cart) {
        if (cart == null) return null;
        return CartDto.builder()
                .cartId(cart.getCartId())
                .userId(cart.getUserId())
                .orderDtos(cart.getOrders()
                        .stream()
                        .map(order -> OrderDto.builder()
                                .orderId(order.getOrderId())
                                .orderDate(order.getOrderDate())
                                .orderDesc(order.getOrderDesc())
                                .orderFee(order.getOrderFee())
                                .build())
                        .collect(Collectors.toSet()))
                .userDto(
                        UserDto.builder()
                                .id(cart.getUserId())
                                .build())
                .build();
    }

    static Cart map(final CartDto cartDto) {
        if (cartDto == null) return null;
        return Cart.builder()
                .cartId(cartDto.getCartId())
                .userId(cartDto.getUserId())
                .orders(cartDto.getOrderDtos()
                        .stream()
                        .map(orderDto -> Order.builder()
                                .orderId(orderDto.getOrderId())
                                .orderDate(orderDto.getOrderDate())
                                .orderDesc(orderDto.getOrderDesc())
                                .orderFee(orderDto.getOrderFee())
                                .build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
