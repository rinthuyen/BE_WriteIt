package com.writeit.write_it.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.writeit.write_it.payload.request.UserLoginRequestDTO;
import com.writeit.write_it.payload.request.UserRefreshRequestDTO;
import com.writeit.write_it.payload.request.UserRegisterRequestDTO;
import com.writeit.write_it.payload.response.UserLoginResponseDTO;
import com.writeit.write_it.payload.response.UserRegisterResponseDTO;
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
    public UserRegisterResponseDTO register(@RequestBody @Valid UserRegisterRequestDTO request) {
        UserRegisterResponseDTO response = authService.register(request);
        return response;
    }

    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody @Valid UserLoginRequestDTO request) {
        UserLoginResponseDTO response = authService.login(request);
        return response;
    }

    @PostMapping("/refresh")
    public UserLoginResponseDTO refresh(@RequestBody @Valid UserRefreshRequestDTO request) {
        UserLoginResponseDTO response = authService.refresh(request.getRefreshToken());
        return response;
    }

}
