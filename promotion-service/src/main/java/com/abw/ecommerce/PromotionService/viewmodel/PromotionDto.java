package com.abw.ecommerce.PromotionService.viewmodel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.abw.ecommerce.PromotionService.model.enumeration.ApplyTo;
import com.abw.ecommerce.PromotionService.model.enumeration.DiscountType;
import com.abw.ecommerce.PromotionService.model.enumeration.UsageType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize
public class PromotionDto {
    @NotNull
    private ApplyTo applyTo;
    @NotNull
    private UsageType usageType;
    @NotNull
    private DiscountType discountType;
    @Max(100)
    private Long discountPercentage;
    private Long discountAmount;
    private int usageLimit;
    private List<Long> productIds;
    private List<Long> brandIds;
    private List<Long> categoryIds;

}
