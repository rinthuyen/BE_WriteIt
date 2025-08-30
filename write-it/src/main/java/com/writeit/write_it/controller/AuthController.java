package com.writeit.write_it.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.writeit.write_it.common.exception.GlobalExceptionHandler;
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

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public Response<Object> register(@RequestBody @Valid RegisterRequestDTO request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return GlobalExceptionHandler.handleValidationException(bindingResult);
        }
        RegisterResponseDTO response = authService.register(request);
        return new Response<>(HttpStatus.CREATED, response);
    }

    @PostMapping("/login")
    public Response<Object> login(@RequestBody @Valid LoginRequestDTO request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return GlobalExceptionHandler.handleValidationException(bindingResult);
        }
        AuthTokenResponseDTO response = authService.login(request);
        return new Response<>(HttpStatus.OK, response);
    }

    @PostMapping("/refresh")
    public Response<Object> refresh(@RequestBody @Valid RefreshRequestDTO request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return GlobalExceptionHandler.handleValidationException(bindingResult);
        }
        AuthTokenResponseDTO response = authService.refresh(request.getRefreshToken());
        return new Response<>(HttpStatus.OK, response);
    }

    @PostMapping("/forgot-password")
    public Response<Object> forgotPassword(@RequestBody @Valid ForgotPasswordRequestDTO request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return GlobalExceptionHandler.handleValidationException(bindingResult);
        }
        authService.forgotPassword(request);
        return new Response<>(HttpStatus.OK, null);
    }
    

    @PostMapping("/reset-password")
    public Response<Object> resetPassword(@RequestBody @Valid ResetPasswordRequestDTO request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return GlobalExceptionHandler.handleValidationException(bindingResult);
        }
        authService.resetPassword(request);
        return new Response<>(HttpStatus.OK, null);
    }
    

}
