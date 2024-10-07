package com.abw.ecommerce.PromotionService.viewmodel;

import com.abw.ecommerce.PromotionService.model.Promotion;
import com.abw.ecommerce.PromotionService.model.PromotionApply;
import com.abw.ecommerce.PromotionService.validation.PromotionConstraint;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@PromotionConstraint
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionPutVm extends PromotionDto {
    @NotNull
    private Long id;
    @Size(min = 1, max = 450)
    private String name;
    @NotBlank
    private String slug;
    private String description;
    @NotBlank
    private String couponCode;
    private Long minimumOrderPurchaseAmount;
    private boolean isActive;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;

    public static List<PromotionApply> createPromotionApplies(PromotionPutVm promotionPutVm, Promotion promotion) {
        return switch (promotion.getApplyTo()) {
            case PRODUCT -> new ArrayList<>(promotionPutVm.getProductIds().stream()
                    .map(productId -> PromotionApply.builder().productId(productId).promotion(promotion)
                            .build())
                    .toList());
            case BRAND -> promotionPutVm.getBrandIds().stream()
                    .map(brandId -> PromotionApply.builder().brandId(brandId).promotion(promotion)
                            .build())
                    .toList();
            case CATEGORY -> promotionPutVm.getCategoryIds().stream()
                    .map(categoryId -> PromotionApply.builder().categoryId(categoryId).promotion(promotion)
                            .build())
                    .toList();
        };
    }
}
