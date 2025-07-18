package com.writeit.write_it.common.mapper;

import com.writeit.write_it.entity.User;
import com.writeit.write_it.payload.request.UserRegisterRequestDTO;
import com.writeit.write_it.payload.response.UserRegisterResponseDTO;

public class UserMapper {
    public static User UserRegisterDTOtoUser(UserRegisterRequestDTO dto) {
        return new User(dto.getUsername(), dto.getPassword(), dto.getDisplayedName());
    }

    public static UserRegisterResponseDTO UserToUserRegisterResponseDTO(User user) {
        return new UserRegisterResponseDTO(user.getDisplayedName(), user.getStatus());
    }
}
