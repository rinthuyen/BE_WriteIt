package com.writeit.write_it.mapper;

import com.writeit.write_it.dto.user.UserRegisterDTO;
import com.writeit.write_it.dto.user.UserRegisterResponseDTO;
import com.writeit.write_it.entity.User;

public class UserMapper {
    public static User UserRegisterDTOtoUser(UserRegisterDTO userRegisterDTO) {
        User user = new User();
        user.setUsername(userRegisterDTO.getUsername());
        user.setPassword(userRegisterDTO.getPassword());
        user.setDisplayedName(userRegisterDTO.getDisplayedName());
        return user;
    }

    public static UserRegisterDTO UserToUserRegisterDTO(User user) {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername(user.getUsername());
        userRegisterDTO.setPassword(user.getPassword());
        userRegisterDTO.setDisplayedName(user.getDisplayedName());
        return userRegisterDTO;
    }

    public static UserRegisterResponseDTO UserToUserRegisterResponseDTO(User user) {
        UserRegisterResponseDTO userRegisterResponseDTO = new UserRegisterResponseDTO();
        userRegisterResponseDTO.setDisplayedName(user.getDisplayedName());
        userRegisterResponseDTO.setStatus(user.getStatus());
        return userRegisterResponseDTO;
    }
}
