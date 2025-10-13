package com.writeit.write_it.common.exception;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.writeit.write_it.dto.response.Response;

import jakarta.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AppException.class)
    public static Response<Object> handleAppException(AppException exception, HttpServletResponse response) {
        response.setStatus(exception.getError().getStatus().value());
        return Response.error(exception.getError());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Response<Object>> handleUnexpected(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.error(ApiError.INTERNAL_ERROR));
    }

    private static final Map<String, Integer> PRIORITY = Map.of(
            "NotBlank", 0, "NotNull", 0, "NotEmpty", 0,
            "Pattern", 1, "Email", 1, "EnumValue", 1,
            "Size", 2, "Min", 2, "Max", 2);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Object>> handleValidation(MethodArgumentNotValidException ex) {

        List<FieldError> sorted = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .sorted(Comparator.comparingInt(fe -> PRIORITY.getOrDefault(fe.getCode(), 100)))
                .collect(Collectors.toList());

        Map<String, String> firstPerField = new LinkedHashMap<>();
        for (FieldError fe : sorted) {
            firstPerField.putIfAbsent(fe.getField(), fe.getDefaultMessage());
        }

        // if i were to have class level errors
        // var globalErrors = ex.getBindingResult().getGlobalErrors();
        // if (!globalErrors.isEmpty()) {
        // String joined = globalErrors.stream()
        // .map(err -> err.getDefaultMessage())
        // .distinct()
        // .collect(Collectors.joining("; "));
        // firstPerField.put("_global", joined);
        // }

        Response<Object> body = Response.error(ApiError.VALIDATION_FAILED)
                .withMeta("errors", firstPerField);

        return ResponseEntity.badRequest().body(body);
    }
}
