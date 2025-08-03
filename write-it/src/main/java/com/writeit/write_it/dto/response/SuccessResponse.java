package com.writeit.write_it.dto.response;

import org.springframework.http.HttpStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SuccessResponse<T> {
    private int status;
    private T data;

    public SuccessResponse(HttpStatus status, T data) {
        this.status = status.value();
        this.data = data;
    }
}
