package com.writeit.write_it.dao.user;

import java.util.Optional;

import com.writeit.write_it.entity.User;

public interface UserDAO {
    User save(User user);

    User findById(Long id);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    // look into whether to use @Transactional or not for these two actions

    // void updateUser(User user);
    // update password and username ykyk (update separately or juse send the whole
    // user object) for both

    // void deleteUser(Long id);
    // self note: turn it to soft delete later on, so change the database schema as
    // well?

    // consider add a deleteAllUsers() method
    // for testing purposes, but not for production use
}
