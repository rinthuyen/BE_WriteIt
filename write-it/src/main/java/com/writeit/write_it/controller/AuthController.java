package com.writeit.write_it.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.writeit.write_it.dto.request.auth_user.ForgotPasswordRequestDTO;
import com.writeit.write_it.dto.request.auth_user.LoginRequestDTO;
import com.writeit.write_it.dto.request.auth_user.RefreshRequestDTO;
import com.writeit.write_it.dto.request.auth_user.RegisterRequestDTO;
import com.writeit.write_it.dto.request.auth_user.ResetPasswordRequestDTO;
import com.writeit.write_it.dto.response.AuthTokenResponseDTO;
import com.writeit.write_it.dto.response.RegisterResponseDTO;
import com.writeit.write_it.dto.response.Response;
import com.writeit.write_it.service.auth.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Response<RegisterResponseDTO>> register(@RequestBody @Valid RegisterRequestDTO request) {
        RegisterResponseDTO response = authService.register(request);
        return ResponseEntity.ok(Response.ok(response, "Registration successful!"));
    }

    @PostMapping("/login")
    public ResponseEntity<Response<AuthTokenResponseDTO>> login(@RequestBody @Valid LoginRequestDTO request) {
        AuthTokenResponseDTO response = authService.login(request);
        return ResponseEntity.ok(Response.ok(response, "Login successful!"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Response<AuthTokenResponseDTO>> refresh(@RequestBody @Valid RefreshRequestDTO request) {
        AuthTokenResponseDTO response = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(Response.ok(response, "Refresh successful!"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Response<Object>> forgotPassword(@RequestBody @Valid ForgotPasswordRequestDTO request) {
        authService.forgotPassword(request);
        return ResponseEntity.ok(Response.ok(null, "Email sent successfully!"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Response<Object>> resetPassword(@RequestBody @Valid ResetPasswordRequestDTO request) {
        authService.resetPassword(request);
        return ResponseEntity.ok(Response.ok(null, "Password reset successful!"));
    }

}
