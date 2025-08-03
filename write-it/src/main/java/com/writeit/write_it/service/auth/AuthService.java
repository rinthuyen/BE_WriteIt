package com.writeit.write_it.service.auth;

import com.writeit.write_it.dto.request.LoginRequestDTO;
import com.writeit.write_it.dto.request.RegisterRequestDTO;
import com.writeit.write_it.dto.response.AuthTokenResponseDTO;
import com.writeit.write_it.dto.response.RegisterResponseDTO;

public interface AuthService {
    RegisterResponseDTO register(RegisterRequestDTO userRegisterRequestDTO);

    AuthTokenResponseDTO login(LoginRequestDTO userLoginRequestDTO);

    AuthTokenResponseDTO refresh(String refreshToken);
}
