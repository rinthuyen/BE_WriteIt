package com.writeit.write_it.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.writeit.write_it.dto.request.UserUpdateRequestDTO;
import com.writeit.write_it.dto.response.Response;
import com.writeit.write_it.service.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PutMapping("/update")
    public Response<Object> update( @RequestBody @Valid UserUpdateRequestDTO request, Authentication authentication) {
        String username = authentication.getName();
        userService.update(request, username);
        return Response.ok(null);
    }
    
}
