package com.abw.ecommerce.RatingService.exception;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(final String message) {
        super(message);
    }
}
