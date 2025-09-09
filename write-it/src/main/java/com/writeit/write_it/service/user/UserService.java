package com.writeit.write_it.service.user;

import com.writeit.write_it.dto.request.UserUpdatePasswordRequestDTO;
import com.writeit.write_it.dto.request.UserUpdateRequestDTO;

public interface UserService {
    void update(UserUpdateRequestDTO request, String username);
    void updatePassword(UserUpdatePasswordRequestDTO request, String username);
}
