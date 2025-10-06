package com.writeit.write_it.service.user;

import com.writeit.write_it.dto.request.auth_user.UserUpdatePasswordRequestDTO;
import com.writeit.write_it.dto.request.auth_user.UserUpdateRequestDTO;

public interface UserService {
    void update(UserUpdateRequestDTO request, Long userId);

    void updatePassword(UserUpdatePasswordRequestDTO request, Long userId);
}
