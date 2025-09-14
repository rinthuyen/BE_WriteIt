package com.writeit.write_it.dao.note;

import com.writeit.write_it.dao.crud.SoftCrudDAOImpl;
import com.writeit.write_it.entity.Note;

import jakarta.persistence.EntityManager;

public class NoteDAOImpl extends SoftCrudDAOImpl<String, Note> implements NoteDAO {
    public NoteDAOImpl(EntityManager entityManager) {
        super(entityManager, Note.class);
    }
}
