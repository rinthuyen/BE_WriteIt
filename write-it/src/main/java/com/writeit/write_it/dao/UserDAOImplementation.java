package com.writeit.write_it.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.writeit.write_it.model.User;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Repository
public class UserDAOImplementation implements UserDAO {
    private EntityManager entityManager;

    @Autowired
    public UserDAOImplementation(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional // for roll back safety
    public void createUser(User user) {
        entityManager.persist(user);
    }

    @Override // its just a query, no need for @Transactional
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

}
