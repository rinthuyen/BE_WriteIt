package com.writeit.write_it.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.writeit.write_it.dto.request.LoginRequestDTO;
import com.writeit.write_it.dto.request.RefreshRequestDTO;
import com.writeit.write_it.dto.request.RegisterRequestDTO;
import com.writeit.write_it.dto.response.AuthTokenResponseDTO;
import com.writeit.write_it.dto.response.RegisterResponseDTO;
import com.writeit.write_it.dto.response.SuccessResponse;
import com.writeit.write_it.service.auth.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse<RegisterResponseDTO> register(@RequestBody @Valid RegisterRequestDTO request) {
        RegisterResponseDTO response = authService.register(request);
        return new SuccessResponse<RegisterResponseDTO>(HttpStatus.CREATED, response);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<AuthTokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {
        AuthTokenResponseDTO response = authService.login(request);
        return new SuccessResponse<AuthTokenResponseDTO>(HttpStatus.OK, response);
    }

    @PostMapping("/refresh")
    public SuccessResponse<AuthTokenResponseDTO> refresh(@RequestBody @Valid RefreshRequestDTO request) {
        AuthTokenResponseDTO response = authService.refresh(request.getRefreshToken());
        return new SuccessResponse<AuthTokenResponseDTO>(HttpStatus.OK, response);
    }

}
