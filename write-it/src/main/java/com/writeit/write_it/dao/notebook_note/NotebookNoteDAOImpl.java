package com.writeit.write_it.dao.notebook_note;

import org.springframework.stereotype.Repository;

import com.writeit.write_it.dao.crud.SoftCrudDAOImpl;
import com.writeit.write_it.entity.NotebookNote;

import jakarta.persistence.EntityManager;

@Repository
public class NotebookNoteDAOImpl extends SoftCrudDAOImpl<Long, NotebookNote> implements NotebookNoteDAO {
    public NotebookNoteDAOImpl(EntityManager entityManager) {
        super(entityManager, NotebookNote.class);
    }
}
