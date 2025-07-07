package com.writeit.write_it.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.writeit.write_it.dto.user.UserRegisterDTO;
import com.writeit.write_it.dto.user.UserRegisterResponseDTO;
import com.writeit.write_it.service.user.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // http://localhost:1000/WriteIt/api/users/hello
    @GetMapping("/hello")
    public String saysHello() {
        return "Hello from UserController!";
    }

    @PostMapping("/register")
    public UserRegisterResponseDTO registerUser(@RequestBody UserRegisterDTO userRegisterDTO) {
        UserRegisterResponseDTO userRegisterResponseDTO = userService.register(
                userRegisterDTO.getUsername(),
                userRegisterDTO.getPassword());
        return userRegisterResponseDTO; // consider returning a response DTO with status
    }

}
