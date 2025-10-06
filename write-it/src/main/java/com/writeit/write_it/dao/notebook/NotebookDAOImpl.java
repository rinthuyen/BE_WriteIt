package com.writeit.write_it.dao.notebook;

import org.springframework.stereotype.Repository;

import com.writeit.write_it.dao.crud.SoftCrudDAOImpl;
import com.writeit.write_it.dto.AllNotebooksDTO;
import com.writeit.write_it.dto.NotebookSummaryDTO;
import com.writeit.write_it.entity.Notebook;

import jakarta.persistence.EntityManager;

@Repository
public class NotebookDAOImpl extends SoftCrudDAOImpl<Long, Notebook> implements NotebookDAO {
    public NotebookDAOImpl(EntityManager entityManager) {
        super(entityManager, Notebook.class);
    }
}
