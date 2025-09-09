package com.writeit.write_it.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.writeit.write_it.common.exception.ApiError;
import com.writeit.write_it.dto.response.Response;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JsonAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper mapper;
    public JsonAccessDeniedHandler(ObjectMapper mapper) { this.mapper = mapper; }

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException ex) throws IOException {
        if (res.isCommitted()) return;
        Response<Object> body = Response.error(ApiError.ACCESS_DENIED);
        res.setStatus(HttpStatus.FORBIDDEN.value());
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        mapper.writeValue(res.getWriter(), body);
        log.warn("Forbidden {} {} from {}", req.getMethod(), req.getRequestURI(), req.getRemoteAddr());
    }
}