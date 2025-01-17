package com.abw.ecommerce.TaxService.viewmodel.taxclass;

import com.abw.ecommerce.TaxService.model.TaxClass;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaxClassPostVm(@NotBlank String id, @Size(min = 1, max = 450) String name) {

    public TaxClass toModel() {
        TaxClass taxClass = new TaxClass();
        taxClass.setName(name);
        return taxClass;
    }

}
