package com.writeit.write_it.common.exception;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.writeit.write_it.dto.response.Response;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AppException.class)
    public static Response<String> handleCustomException(
            AppException exception) {
        Response<String> response = new Response<>();
        switch (exception.getMessage()) {
            case ExceptionMessage.INVALID_CREDENTIALS -> response
                        .setStatus(HttpStatus.UNAUTHORIZED)
                        .setData(ExceptionMessage.INVALID_CREDENTIALS);
            case ExceptionMessage.USER_DEACTIVATED -> response
                        .setStatus(HttpStatus.FORBIDDEN)
                        .setData(ExceptionMessage.USER_DEACTIVATED);
            case ExceptionMessage.INVALID_REFRESH_TOKEN -> response
                        .setStatus(HttpStatus.UNAUTHORIZED)
                        .setData(ExceptionMessage.INVALID_REFRESH_TOKEN);
            case ExceptionMessage.USERNAME_ALREADY_EXISTS -> response
                        .setStatus(HttpStatus.CONFLICT)
                        .setData(ExceptionMessage.USERNAME_ALREADY_EXISTS);
            case ExceptionMessage.NO_USER_WITH_GIVEN_USERNAME -> response
                        .setStatus(HttpStatus.BAD_REQUEST)
                        .setData(ExceptionMessage.NO_USER_WITH_GIVEN_USERNAME);
            case ExceptionMessage.NO_USER_WITH_GIVEN_EMAIL -> response
                        .setStatus(HttpStatus.BAD_REQUEST)
                        .setData(ExceptionMessage.NO_USER_WITH_GIVEN_EMAIL);
            case ExceptionMessage.INVALID_TOKEN_PURPOSE -> response
                        .setStatus(HttpStatus.UNPROCESSABLE_ENTITY)
                        .setData(ExceptionMessage.INVALID_TOKEN_PURPOSE);
            default -> {
            }
        }
        return response;
    }

    public static Response<Object> handleValidationException(BindingResult bindingResult) {
        Optional<String> error = bindingResult
                .getAllErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .findFirst();
        return new Response<>(HttpStatus.BAD_REQUEST, error);

    }
}
