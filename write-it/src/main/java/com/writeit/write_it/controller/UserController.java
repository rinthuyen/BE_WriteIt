package com.writeit.write_it.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.writeit.write_it.dto.request.auth_user.UserUpdatePasswordRequestDTO;
import com.writeit.write_it.dto.request.auth_user.UserUpdateRequestDTO;
import com.writeit.write_it.dto.response.Response;
import com.writeit.write_it.security.userdetails.CustomUserDetails;
import com.writeit.write_it.service.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("/update")
    public ResponseEntity<Response<Object>> update(@RequestBody @Valid UserUpdateRequestDTO request,
            @AuthenticationPrincipal CustomUserDetails user) {
        Long userId = user.getId();
        userService.update(request, userId);
        return ResponseEntity.ok(Response.ok(null, "Update successful!"));
    }

    @PutMapping("/update-password")
    public ResponseEntity<Response<Object>> updatePassword(@RequestBody @Valid UserUpdatePasswordRequestDTO request,
            @AuthenticationPrincipal CustomUserDetails user) {
        Long userId = user.getId();
        userService.updatePassword(request, userId);
        return ResponseEntity.ok(Response.ok(null, "Update successful!"));
    }
}
