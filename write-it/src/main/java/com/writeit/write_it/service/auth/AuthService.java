package com.writeit.write_it.service.auth;

import com.writeit.write_it.payload.request.UserLoginRequestDTO;
import com.writeit.write_it.payload.request.UserRegisterRequestDTO;
import com.writeit.write_it.payload.response.UserLoginResponseDTO;
import com.writeit.write_it.payload.response.UserRegisterResponseDTO;

public interface AuthService {
    UserRegisterResponseDTO register(UserRegisterRequestDTO userRegisterRequestDTO);

    UserLoginResponseDTO login(UserLoginRequestDTO userLoginRequestDTO);

    UserLoginResponseDTO refresh(String refreshToken);
}
