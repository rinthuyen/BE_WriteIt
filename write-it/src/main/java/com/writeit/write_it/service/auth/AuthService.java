package com.writeit.write_it.service.auth;

import com.writeit.write_it.dto.user.UserRegisterDTO;
import com.writeit.write_it.dto.user.UserRegisterResponseDTO;

public interface AuthService {
    UserRegisterResponseDTO register(UserRegisterDTO userRegisterDTO);
}
