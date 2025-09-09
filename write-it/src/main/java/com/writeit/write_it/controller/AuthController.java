package com.writeit.write_it.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.writeit.write_it.dto.request.ForgotPasswordRequestDTO;
import com.writeit.write_it.dto.request.LoginRequestDTO;
import com.writeit.write_it.dto.request.RefreshRequestDTO;
import com.writeit.write_it.dto.request.RegisterRequestDTO;
import com.writeit.write_it.dto.request.ResetPasswordRequestDTO;
import com.writeit.write_it.dto.response.AuthTokenResponseDTO;
import com.writeit.write_it.dto.response.RegisterResponseDTO;
import com.writeit.write_it.dto.response.Response;
import com.writeit.write_it.service.auth.AuthService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Response<Object>> register(@RequestBody @Valid  RegisterRequestDTO request) {
        RegisterResponseDTO response = authService.register(request);
        return ResponseEntity.ok(Response.ok(response));
    }

    //@CrossOrigin(origins = "http://localhost:4200") // Apply to this method
    @PostMapping("/login")
    public ResponseEntity<Response<Object>> login(@RequestBody @Valid LoginRequestDTO request) {
        AuthTokenResponseDTO response = authService.login(request);
        return ResponseEntity.ok(Response.ok(response));
    }

    @PostMapping("/refresh")
    public Response<Object> refresh(@RequestBody @Valid RefreshRequestDTO request) {
        AuthTokenResponseDTO response = authService.refresh(request.getRefreshToken());
        return Response.ok(response);
    }

    @PostMapping("/forgot-password")
    public Response<Object> forgotPassword(@RequestBody @Valid ForgotPasswordRequestDTO request) {
        authService.forgotPassword(request);
        return Response.ok(null);
    }
    

    @PostMapping("/reset-password")
    public Response<Object> resetPassword(@RequestBody @Valid ResetPasswordRequestDTO request) {
        authService.resetPassword(request);
        return Response.ok(null);
    }
    

}
