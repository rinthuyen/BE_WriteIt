package com.writeit.write_it.service.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.writeit.write_it.common.exception.ApiError;
import com.writeit.write_it.common.exception.AppException;
import com.writeit.write_it.dao.user.UserDAO;
import com.writeit.write_it.dto.request.UserUpdatePasswordRequestDTO;
import com.writeit.write_it.dto.request.UserUpdateRequestDTO;
import com.writeit.write_it.entity.User;

@Service
public class UserServiceImpl implements UserService{
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void update(UserUpdateRequestDTO request, String username) {
        User user = userDAO
            .findByUsername(username)
            .orElseThrow(() -> new AppException(ApiError.USER_NOT_FOUND));
        user.setDisplayedName(request.getDisplayedName());
        user.setEmail(request.getEmail());
        user.setStatus(request.getStatus());
        userDAO.update(user);
    }

    @Override
    @Transactional
    public void updatePassword(UserUpdatePasswordRequestDTO request, String username) {
        User user = userDAO
            .findByUsername(username)
            .orElseThrow(() -> new AppException(ApiError.USER_NOT_FOUND));
        String encoded = passwordEncoder.encode(request.getPassword());
        user.setPassword(encoded);
        userDAO.update(user);
    }
}
