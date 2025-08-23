package com.writeit.write_it.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.writeit.write_it.common.exception.GlobalExceptionHandler;
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
    public Response<Object> update( @RequestBody @Valid UserUpdateRequestDTO request, BindingResult bindingResult, Authentication authentication) {
        if (bindingResult.hasErrors()){
            return GlobalExceptionHandler.handleValidationException(bindingResult);
        }
        String username = authentication.getName();
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // User user = (User) authentication.getPrincipal();

        // if (!user.getUsername().equals(username)) {
        //     return new Response<>(HttpStatus.UNAUTHORIZED, null);
        // }
        userService.update(request, username);
        return new Response<>(HttpStatus.OK, null);
    }
    
}
