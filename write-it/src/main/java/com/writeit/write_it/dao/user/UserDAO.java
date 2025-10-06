package com.writeit.write_it.dao.user;

import java.util.Optional;

import com.writeit.write_it.dao.crud.CrudDAO;
import com.writeit.write_it.entity.User;

public interface UserDAO extends CrudDAO<Long, User> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean isEmailTakenByAnotherUser(String email, Long excludeId);
}
