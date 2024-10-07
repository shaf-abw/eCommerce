package com.abw.ecommerce.PromotionService.exception;

import com.abw.ecommerce.PromotionService.utils.MessagesUtils;

public class DuplicatedException extends RuntimeException {

    private String message;

    public DuplicatedException(String message, Object... var2) {
        this.message = MessagesUtils.getMessage(message, var2);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
