package com.writeit.write_it.dto.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private HttpStatus status;
    private String message;

    public ErrorResponse setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public ErrorResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}
