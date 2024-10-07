package com.abw.ecommerce.UserService.model.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class ResetPasswordRequest {
    private String email;
    private String newPassword;

}
