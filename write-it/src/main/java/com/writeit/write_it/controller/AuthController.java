package com.writeit.write_it.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.writeit.write_it.dto.user.UserRegisterDTO;
import com.writeit.write_it.dto.user.UserRegisterResponseDTO;
import com.writeit.write_it.service.auth.AuthService;

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
    public UserRegisterResponseDTO registerUser(@RequestBody UserRegisterDTO userRegisterDTO) {
        UserRegisterResponseDTO userRegisterResponseDTO = authService.register(
                userRegisterDTO.getUsername(),
                userRegisterDTO.getPassword(),
                userRegisterDTO.getDisplayedName());
        return userRegisterResponseDTO;
    }

}
