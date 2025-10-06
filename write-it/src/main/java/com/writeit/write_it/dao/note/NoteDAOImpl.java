package com.writeit.write_it.dao.note;

import org.springframework.stereotype.Repository;

import com.writeit.write_it.dao.crud.SoftCrudDAOImpl;
import com.writeit.write_it.entity.Note;

import jakarta.persistence.EntityManager;

@Repository
public class NoteDAOImpl extends SoftCrudDAOImpl<Long, Note> implements NoteDAO {
    public NoteDAOImpl(EntityManager entityManager) {
        super(entityManager, Note.class);
    }
}
