package com.writeit.write_it.security.jwt;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.writeit.write_it.common.exception.ApiError;
import com.writeit.write_it.dto.response.Response;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper;

    public JwtAuthenticationEntryPoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException ex) throws IOException {

        if (response.isCommitted()) {
            log.warn("Response already committed for {} — skipping entry point", request.getRequestURI());
            return;
        }

        ApiError apiError = ApiError.AUTHENTICATION_FAILED;

        Response<Object> body = Response.error(apiError);

        response.setStatus(apiError.getStatus().value());   // 401
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        mapper.writeValue(response.getWriter(), body);

        log.warn("Unauthorized {} {} from {}: {}",
                request.getMethod(), request.getRequestURI(),
                request.getRemoteAddr(), ex.getClass().getSimpleName());
        log.debug("Auth failure detail: {}", ex.getMessage());
    }
}
