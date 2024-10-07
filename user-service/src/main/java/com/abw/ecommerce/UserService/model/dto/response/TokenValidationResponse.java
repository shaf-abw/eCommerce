package com.abw.ecommerce.UserService.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenValidationResponse {
    private String message;

    public TokenValidationResponse() {
    }

    public TokenValidationResponse(String message) {
        this.message = message;
    }

}
