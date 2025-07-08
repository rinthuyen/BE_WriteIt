package com.writeit.write_it.dao.user;

import org.springframework.stereotype.Repository;

import com.writeit.write_it.dto.user.UserRegisterDTO;
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
    @Transactional // for roll back safety
    public User create(UserRegisterDTO userRegisterDTO) {
        User user = new User(userRegisterDTO.getUsername(),
                userRegisterDTO.getPassword(),
                userRegisterDTO.getDisplayedName());
        entityManager.persist(user);
        return user;
    }

    // @Override // its just a query, no need for @Transactional
    // public User getUserById(Long id) {
    // return entityManager.find(User.class, id);
    // }

    // @Override
    // public Optional<User> getUserByUsername(String username) {
    // // use getresultlist to avoid exceptions if no user is found
    // List<User> user = entityManager.createQuery("SELECT u FROM User u WHERE
    // u.username = :username", User.class)
    // .setParameter("username", username)
    // .getResultList();
    // return user.stream().findFirst();
    // }

}
