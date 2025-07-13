package com.writeit.write_it.mapper;

import com.writeit.write_it.dto.user.UserRegisterDTO;
import com.writeit.write_it.dto.user.UserRegisterResponseDTO;
import com.writeit.write_it.entity.User;

public class UserMapper {
    public static User UserRegisterDTOtoUser(UserRegisterDTO dto) {
        return new User(dto.getUsername(), dto.getPassword(), dto.getDisplayedName());
    }

    public static UserRegisterResponseDTO UserToUserRegisterResponseDTO(User user) {
        return new UserRegisterResponseDTO(user.getDisplayedName(), user.getStatus());
    }
}
