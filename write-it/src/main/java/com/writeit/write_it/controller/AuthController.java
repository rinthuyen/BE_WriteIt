package com.writeit.write_it.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.writeit.write_it.common.exception.GlobalExceptionHandler;
import com.writeit.write_it.dto.request.LoginRequestDTO;
import com.writeit.write_it.dto.request.RefreshRequestDTO;
import com.writeit.write_it.dto.request.RegisterRequestDTO;
import com.writeit.write_it.dto.response.AuthTokenResponseDTO;
import com.writeit.write_it.dto.response.RegisterResponseDTO;
import com.writeit.write_it.dto.response.Response;
import com.writeit.write_it.service.auth.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
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
    public Response<Object> register(@RequestBody @Valid RegisterRequestDTO request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return GlobalExceptionHandler.handleValidationException(bindingResult);
        }
        RegisterResponseDTO response = authService.register(request);
        return new Response<Object>(HttpStatus.CREATED, response);
    }

    //@CrossOrigin(origins = "http://localhost:4200") // Apply to this method
    @PostMapping("/login")
    public Response<Object> login(@RequestBody @Valid LoginRequestDTO request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return GlobalExceptionHandler.handleValidationException(bindingResult);
        }
        AuthTokenResponseDTO response = authService.login(request);
        return new Response<Object>(HttpStatus.OK, response);
    }

    @PostMapping("/refresh")
    public Response<Object> refresh(@RequestBody @Valid RefreshRequestDTO request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return GlobalExceptionHandler.handleValidationException(bindingResult);
        }
        AuthTokenResponseDTO response = authService.refresh(request.getRefreshToken());
        return new Response<Object>(HttpStatus.OK, response);
    }

}
