package com.abw.ecommerce.RatingService.viewmodel;


import com.abw.ecommerce.RatingService.model.Rating;
import java.time.ZonedDateTime;
import lombok.Builder;

@Builder
public record RatingVm(Long id, String content, int star, Long productId, String productName, String createdBy,
                       String lastName, String firstName, ZonedDateTime createdOn) {
    public static RatingVm fromModel(Rating rating) {
        return RatingVm.builder()
                .id(rating.getId())
                .content(rating.getContent())
                .star(rating.getRatingStar())
                .productId(rating.getProductId())
                .productName(rating.getProductName())
                .lastName(rating.getLastName())
                .firstName(rating.getFirstName())
                .createdBy(rating.getCreatedBy())
                .createdOn(rating.getCreatedOn())
                .build();
    }
}
