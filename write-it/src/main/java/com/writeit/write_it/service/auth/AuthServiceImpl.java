package com.writeit.write_it.service.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.writeit.write_it.dao.user.UserDAO;
import com.writeit.write_it.entity.User;
import com.writeit.write_it.exception.UsernameAlreadyExistsException;
import com.writeit.write_it.mapper.UserMapper;
import com.writeit.write_it.payload.request.UserRegisterDTO;
import com.writeit.write_it.payload.response.UserRegisterResponseDTO;

import jakarta.transaction.Transactional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserRegisterResponseDTO register(UserRegisterDTO userRegisterDTO) {
        if (userDAO.existsByUsername(userRegisterDTO.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }

        String encodedPassword = passwordEncoder.encode(userRegisterDTO.getPassword());
        userRegisterDTO.setPassword(encodedPassword);

        User user = UserMapper.UserRegisterDTOtoUser(userRegisterDTO);
        userDAO.create(user);
        UserRegisterResponseDTO userRegisterResponseDTO = UserMapper.UserToUserRegisterResponseDTO(user);
        return userRegisterResponseDTO;
    }
}
