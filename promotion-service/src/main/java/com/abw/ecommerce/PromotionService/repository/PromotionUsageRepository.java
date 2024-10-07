package com.abw.ecommerce.PromotionService.repository;

import com.abw.ecommerce.PromotionService.model.PromotionUsage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionUsageRepository extends JpaRepository<PromotionUsage, Long> {
    boolean existsByPromotionId(Long promotionId);
}
