package com.writeit.write_it.dao.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.writeit.write_it.dao.crud.CrudDAOImpl;
import com.writeit.write_it.entity.User;

import jakarta.persistence.EntityManager;

@Repository
public class UserDAOImpl extends CrudDAOImpl<Long, User> implements UserDAO {
    public UserDAOImpl(EntityManager entityManager) {
        super(entityManager, User.class);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        List<User> user = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList();
        return user.stream().findFirst();
    }

    @Override
    public boolean existsByUsername(String username) {
        Long count = entityManager
                .createQuery("SELECT COUNT(user) FROM User user WHERE user.username = :username", Long.class)
                .setParameter("username", username)
                .getSingleResult();
        return count > 0;
    }
}