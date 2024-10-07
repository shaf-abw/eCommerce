package com.abw.ecommerce.ProxyClient.business.payment.model.response;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

import com.abw.ecommerce.ProxyClient.business.payment.model.PaymentDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentPaymentServiceDtoCollectionResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Collection<PaymentDto> collection;

}
