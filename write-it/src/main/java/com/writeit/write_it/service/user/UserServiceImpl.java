package com.writeit.write_it.service.user;

//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.writeit.write_it.dao.user.UserDAO;
import com.writeit.write_it.dto.user.UserRegisterDTO;
import com.writeit.write_it.dto.user.UserRegisterResponseDTO;
import com.writeit.write_it.entity.User;
import com.writeit.write_it.mapper.UserMapper;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    // private final PasswordEncoder passwordEncoder;

    // public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
    // this.userDAO = userDAO;
    // this.passwordEncoder = passwordEncoder;
    // }
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public UserRegisterResponseDTO register(String username, String password, String displayedName) {
        // if (userDAO.existsByUsername(username)) {
        // throw new IllegalArgumentException("User already exists");
        // }

        // String encodedPassword = passwordEncoder.encode(password);
        // UserRegisterDTO userRegisterDTO = new UserRegisterDTO(username,
        // encodedPassword);
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO(username, password, displayedName);
        User user = userDAO.create(userRegisterDTO);
        UserRegisterResponseDTO userRegisterResponseDTO = UserMapper.UserToUserRegisterResponseDTO(user);
        return userRegisterResponseDTO;
    }
}
