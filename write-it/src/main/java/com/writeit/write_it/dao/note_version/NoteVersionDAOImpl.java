package com.writeit.write_it.dao.note_version;

import com.writeit.write_it.dao.crud.SoftCrudDAOImpl;
import com.writeit.write_it.entity.NoteVersion;

import jakarta.persistence.EntityManager;

public class NoteVersionDAOImpl extends SoftCrudDAOImpl<String, NoteVersion> implements NoteVersionDAO {
    public NoteVersionDAOImpl(EntityManager entityManager) {
        super(entityManager, NoteVersion.class);
    }
}
