package com.writeit.write_it.service.auth;

import com.writeit.write_it.dto.request.auth_user.ForgotPasswordRequestDTO;
import com.writeit.write_it.dto.request.auth_user.LoginRequestDTO;
import com.writeit.write_it.dto.request.auth_user.RegisterRequestDTO;
import com.writeit.write_it.dto.request.auth_user.ResetPasswordRequestDTO;
import com.writeit.write_it.dto.response.AuthTokenResponseDTO;
import com.writeit.write_it.dto.response.RegisterResponseDTO;

public interface AuthService {
    RegisterResponseDTO register(RegisterRequestDTO request);

    AuthTokenResponseDTO login(LoginRequestDTO request);

    AuthTokenResponseDTO refresh(String refreshToken);

    void forgotPassword(ForgotPasswordRequestDTO request);

    void resetPassword(ResetPasswordRequestDTO request);
}
