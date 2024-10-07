package com.abw.ecommerce.RatingService.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ecommerce.services")
public record ServiceUrlConfig(
        String product, String customer, String order) {
}
