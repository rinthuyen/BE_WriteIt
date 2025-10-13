package com.writeit.write_it.dao.note_version;

import org.springframework.stereotype.Repository;

import com.writeit.write_it.dao.crud.SoftCrudDAOImpl;
import com.writeit.write_it.entity.NoteVersion;

import jakarta.persistence.EntityManager;

@Repository
public class NoteVersionDAOImpl extends SoftCrudDAOImpl<Long, NoteVersion> implements NoteVersionDAO {
    public NoteVersionDAOImpl(EntityManager entityManager) {
        super(entityManager, NoteVersion.class);
    }
}
