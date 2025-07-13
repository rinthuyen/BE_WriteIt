package com.writeit.write_it.dao.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.writeit.write_it.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Repository
public class UserDAOImpl implements UserDAO {
    private EntityManager entityManager;

    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public User create(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
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