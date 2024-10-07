package com.abw.ecommerce.SearchService.viewmodel;

import com.abw.ecommerce.SearchService.document.Product;

public record ProductNameGetVm(String name) {
    public static ProductNameGetVm fromModel(Product product) {
        return new ProductNameGetVm(
                product.getName()
        );
    }
}
