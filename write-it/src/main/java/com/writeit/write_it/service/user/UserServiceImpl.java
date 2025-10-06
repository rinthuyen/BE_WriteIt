package com.writeit.write_it.service.user;

import java.util.Locale;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.writeit.write_it.common.exception.ApiError;
import com.writeit.write_it.common.exception.AppException;
import com.writeit.write_it.dao.user.UserDAO;
import com.writeit.write_it.dto.request.auth_user.UserUpdatePasswordRequestDTO;
import com.writeit.write_it.dto.request.auth_user.UserUpdateRequestDTO;
import com.writeit.write_it.entity.User;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void update(UserUpdateRequestDTO request, Long userId) {
        User user = userDAO
                .findById(userId)
                .orElseThrow(() -> new AppException(ApiError.USER_NOT_FOUND));

        String email = request.getEmail();
        if (email != null) {
            email = email.trim();
            if (email.isEmpty()) {
                email = null;
            } else {
                email = email.toLowerCase(Locale.ROOT);
            }
        }

        if (email != null && userDAO.isEmailTakenByAnotherUser(email, userId)) {
            throw new AppException(ApiError.EMAIL_ALREADY_EXISTS);
        }

        user.setDisplayedName(request.getDisplayedName());
        user.setEmail(email);
        user.setStatus(request.getStatus());

        userDAO.update(user);
    }

    @Override
    @Transactional
    public void updatePassword(UserUpdatePasswordRequestDTO request, Long userId) {
        User user = userDAO
                .findById(userId)
                .orElseThrow(() -> new AppException(ApiError.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new AppException(ApiError.CURRENT_PASSWORD_INVALID);
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new AppException(ApiError.NEW_PASSWORD_SAME_AS_OLD);
        }

        String encoded = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(encoded);

        userDAO.update(user);
    }
}
