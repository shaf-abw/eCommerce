package com.abw.ecommerce.UserService.model.dto.response;

import lombok.*;

import jakarta.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ResponseMessage {
    @Size(min = 10, max = 500)
    private String message;
}
