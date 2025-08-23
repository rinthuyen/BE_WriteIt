package com.writeit.write_it.service.user;

import com.writeit.write_it.dto.request.UserUpdateRequestDTO;

public interface UserService {
    void update(UserUpdateRequestDTO request, String username);
}
