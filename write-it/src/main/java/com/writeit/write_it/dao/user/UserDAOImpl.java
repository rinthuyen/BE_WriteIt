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
        String query = "SELECT u FROM User u WHERE u.username = :username";
        List<User> user = entityManager.createQuery(query, User.class)
                .setParameter("username", username)
                .getResultList();
        return user.stream().findFirst();
    }

    @Override
    public boolean existsByUsername(String username) {
        String query = "SELECT COUNT(user) FROM User user WHERE user.username = :username";
        Long count = entityManager
                .createQuery(query, Long.class)
                .setParameter("username", username)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String query = "SELECT u FROM User u WHERE u.email = :email";
        List<User> user = entityManager.createQuery(query, User.class)
                .setParameter("email", email)
                .getResultList();
        return user.stream().findFirst();
    }

    @Override
    public boolean isEmailTakenByAnotherUser(String email, Long excludeId) {
        String query = "SELECT COUNT(u.id) FROM User u WHERE u.email = :email AND u.id <> :excludeId";
        Long count = entityManager.createQuery(query, Long.class)
                .setParameter("email", email)
                .setParameter("excludeId", excludeId)
                .getSingleResult();
        return count > 0;
    }
}