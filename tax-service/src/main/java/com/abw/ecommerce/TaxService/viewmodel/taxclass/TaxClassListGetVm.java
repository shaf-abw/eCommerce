package com.abw.ecommerce.TaxService.viewmodel.taxclass;

import java.util.List;

public record TaxClassListGetVm(
    List<TaxClassVm> taxClassContent,
    int pageNo,
    int pageSize,
    int totalElements,
    int totalPages,
    boolean isLast
) {

}
