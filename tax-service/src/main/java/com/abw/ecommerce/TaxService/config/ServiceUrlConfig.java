package com.abw.ecommerce.TaxService.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ecommerce.services")
public record ServiceUrlConfig(String location) {
}
