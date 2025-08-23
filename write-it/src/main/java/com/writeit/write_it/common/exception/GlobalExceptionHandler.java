package com.writeit.write_it.common.exception;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.writeit.write_it.dto.response.Response;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public static Response<String> handleCustomException(
            CustomException exception) {
        Response<String> response = new Response<>();
        switch (exception.getMessage()) {
            case ExceptionMessage.InvalidCredentials:
                response
                        .setStatus(HttpStatus.UNAUTHORIZED)
                        .setData(ExceptionMessage.InvalidCredentials);
                break;
            case ExceptionMessage.UserDeactivated:
                response
                        .setStatus(HttpStatus.FORBIDDEN)
                        .setData(ExceptionMessage.UserDeactivated);
                break;
            case ExceptionMessage.InvalidRefreshToken:
                response
                        .setStatus(HttpStatus.UNAUTHORIZED)
                        .setData(ExceptionMessage.InvalidRefreshToken);
                break;
            case ExceptionMessage.UsernameAlreadyExists:
                response
                        .setStatus(HttpStatus.CONFLICT)
                        .setData(ExceptionMessage.UsernameAlreadyExists);
                break;
            case ExceptionMessage.NotFoundException:
                response
                        .setStatus(HttpStatus.BAD_REQUEST)
                        .setData(ExceptionMessage.NotFoundException);
                break;
            default:
                break;
        }
        return response;
    }

    public static Response<Object> handleValidationException(BindingResult bindingResult) {
        Optional<String> error = bindingResult
                .getAllErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .findFirst();
        return new Response<Object>(HttpStatus.BAD_REQUEST, error);

    }
}
