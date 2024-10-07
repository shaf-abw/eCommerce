package com.abw.ecommerce.TaxService.viewmodel.taxclass;

import com.abw.ecommerce.TaxService.model.TaxClass;

public record TaxClassVm(Long id, String name) {

    public static TaxClassVm fromModel(TaxClass taxClass) {
        return new TaxClassVm(taxClass.getId(), taxClass.getName());
    }
}
