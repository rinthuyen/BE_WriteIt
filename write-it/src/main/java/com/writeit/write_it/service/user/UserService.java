package com.writeit.write_it.service.user;

import com.writeit.write_it.dto.user.UserRegisterResponseDTO;

public interface UserService {
    UserRegisterResponseDTO register(String username, String password, String displayedName);
}
