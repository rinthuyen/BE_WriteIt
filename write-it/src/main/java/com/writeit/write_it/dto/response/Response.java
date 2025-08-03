package com.writeit.write_it.dto.response;

import org.springframework.http.HttpStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Response<T> {
    private int status;
    private T data;

    public Response(HttpStatus status, T data) {
        this.status = status.value();
        this.data = data;
    }

    public Response<T> setStatus(HttpStatus status) {
        this.status = status.value();
        return this;
    }

    public Response<T> setData(T data) {
        this.data = data;
        return this;
    }
}
