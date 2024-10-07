package com.abw.ecommerce.ProductService.repository;

import com.abw.ecommerce.ProductService.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Page<Category> findAll(Pageable pageable);

    // Thêm các phương thức tìm kiếm hoặc lọc tại đây
    Page<Category> findByCategoryTitleContaining(String categoryTitle, Pageable pageable);

}
