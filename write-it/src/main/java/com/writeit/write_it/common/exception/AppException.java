package com.writeit.write_it.common.exception;

import java.util.Objects;

public class AppException extends RuntimeException {
    private final ApiError error;

    public AppException(ApiError error) {
        super(error.name());
        this.error = Objects.requireNonNull(error, "error must not be null");
    }

    public AppException(ApiError error, Throwable cause) {
        super(error.name(), cause); 
        this.error = Objects.requireNonNull(error, "error must not be null");
    }

    public ApiError getError() { return error; }
}
