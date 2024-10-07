package com.abw.ecommerce.ProductService.repository;

import com.abw.ecommerce.ProductService.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
