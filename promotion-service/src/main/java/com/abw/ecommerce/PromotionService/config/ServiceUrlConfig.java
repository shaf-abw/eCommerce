package com.abw.ecommerce.PromotionService.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ecommerce.services")
public record ServiceUrlConfig(String product) {
}
