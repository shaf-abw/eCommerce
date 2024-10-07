package com.abw.ecommerce.OrderService.repository;

import com.abw.ecommerce.OrderService.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

}