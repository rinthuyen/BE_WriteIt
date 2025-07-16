package com.writeit.write_it.service.auth;

import com.writeit.write_it.payload.request.UserRegisterDTO;
import com.writeit.write_it.payload.response.UserRegisterResponseDTO;

public interface AuthService {
    UserRegisterResponseDTO register(UserRegisterDTO userRegisterDTO);
}
