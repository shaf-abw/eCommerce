package com.abw.ecommerce.ShippingService.repository;

import com.abw.ecommerce.ShippingService.domain.OrderItem;
import com.abw.ecommerce.ShippingService.domain.id.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {

}