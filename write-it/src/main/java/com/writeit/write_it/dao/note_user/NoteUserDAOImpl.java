package com.writeit.write_it.dao.note_user;

import org.springframework.stereotype.Repository;

import com.writeit.write_it.dao.crud.SoftCrudDAOImpl;
import com.writeit.write_it.entity.NoteUser;

import jakarta.persistence.EntityManager;

@Repository
public class NoteUserDAOImpl extends SoftCrudDAOImpl<Long, NoteUser> implements NoteUserDAO {
    public NoteUserDAOImpl(EntityManager entityManager) {
        super(entityManager, NoteUser.class);
    }
}
