package com.writeit.write_it.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.writeit.write_it.common.exception.ApiError;
import com.writeit.write_it.common.exception.AppException;
import com.writeit.write_it.dao.user.UserDAO;
import com.writeit.write_it.dto.request.UserUpdateRequestDTO;
import com.writeit.write_it.entity.User;

@Service
public class UserServiceImpl implements UserService{
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO){
        this.userDAO = userDAO;
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

}
