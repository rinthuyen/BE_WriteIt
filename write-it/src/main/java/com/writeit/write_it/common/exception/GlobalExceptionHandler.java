package com.writeit.write_it.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.writeit.write_it.dto.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public static ErrorResponse handleCustomException(
            CustomException exception) {
        ErrorResponse response = new ErrorResponse();
        switch (exception.getMessage()) {
            case ExceptionMessage.InvalidCredentials:
                response
                        .setStatus(HttpStatus.UNAUTHORIZED)
                        .setMessage(ExceptionMessage.InvalidCredentials);
                break;
            case ExceptionMessage.InvalidRefreshToken:
                response
                        .setStatus(HttpStatus.UNAUTHORIZED)
                        .setMessage(ExceptionMessage.InvalidRefreshToken);
                break;
            case ExceptionMessage.UsernameAlreadyExists:
                response
                        .setStatus(HttpStatus.CONFLICT)
                        .setMessage(ExceptionMessage.UsernameAlreadyExists);
                break;
            default:
                break;
        }
        return response;
    }
}
