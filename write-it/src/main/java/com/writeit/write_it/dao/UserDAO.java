package com.writeit.write_it.dao;

import com.writeit.write_it.model.User;

public interface UserDAO {
    void createUser(User user);

    User getUserById(Long id);

    // User getUserByUsername(String username);

    // void updateUser(User user);

    // void deleteUser(Long id);
}
