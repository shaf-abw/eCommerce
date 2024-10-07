package com.abw.ecommerce.PromotionService.viewmodel;


import java.time.ZonedDateTime;

public record ProductVm(Long id,
                        String name,
                        String slug,
                        Boolean isAllowedToOrder,
                        Boolean isPublished,
                        Boolean isFeatured,
                        Boolean isVisibleIndividually,
                        ZonedDateTime createdOn,
                        Long taxClassId) {
}
