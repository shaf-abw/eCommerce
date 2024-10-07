package com.abw.ecommerce.ProxyClient.exception;

import com.abw.ecommerce.ProxyClient.exception.payload.ExceptionMsg;
import feign.FeignException;
import feign.FeignException.FeignClientException;
import feign.FeignException.FeignServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.abw.ecommerce.ProxyClient.exception.wrapper.CredentialNotFoundException;
import com.abw.ecommerce.ProxyClient.exception.wrapper.FavouriteNotFoundException;
import com.abw.ecommerce.ProxyClient.exception.wrapper.UserObjectNotFoundException;
import com.abw.ecommerce.ProxyClient.exception.wrapper.VerificationTokenNotFoundException;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ApiExceptionHandler {

    @ExceptionHandler(value = {
            FeignClientException.class,
            FeignServerException.class,
            FeignException.class
    })
    public <T extends FeignException> ResponseEntity<ExceptionMsg> handleProxyException(final T e) {
        log.info("ApiExceptionHandler controller, handle feign proxy exception");
        final var badRequest = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(
                ExceptionMsg.builder()
                        .msg(e.contentUTF8())
                        .httpStatus(badRequest)
                        .timestamp(ZonedDateTime
                                .now(ZoneId.systemDefault()))
                        .build(), badRequest);
    }

    @ExceptionHandler(value = {
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class
    })
    public <T extends BindException> ResponseEntity<ExceptionMsg> handleValidationException(final T e) {
        log.info("ApiExceptionHandler controller, handle validation exception");
        final var badRequest = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(
                ExceptionMsg.builder()
                        .msg(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage())
                        .httpStatus(badRequest)
                        .timestamp(ZonedDateTime
                                .now(ZoneId.systemDefault()))
                        .build(), badRequest);
    }

    @ExceptionHandler(value = {
            UserObjectNotFoundException.class,
            CredentialNotFoundException.class,
            VerificationTokenNotFoundException.class,
            FavouriteNotFoundException.class,
            IllegalStateException.class,
    })
    public <T extends RuntimeException> ResponseEntity<ExceptionMsg> handleApiRequestException(final T e) {
        log.info("ApiExceptionHandler controller, handle API request");
        final var badRequest = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(
                ExceptionMsg.builder()
                        .msg(e.getMessage())
                        .httpStatus(badRequest)
                        .timestamp(ZonedDateTime
                                .now(ZoneId.systemDefault()))
                        .build(), badRequest);
    }

}