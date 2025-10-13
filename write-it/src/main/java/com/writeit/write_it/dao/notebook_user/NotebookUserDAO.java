package com.writeit.write_it.dao.notebook_user;

import com.writeit.write_it.dao.crud.SoftCrudDAO;
import com.writeit.write_it.dto.AllNotebooksDTO;
import com.writeit.write_it.entity.NotebookUser;

public interface NotebookUserDAO extends SoftCrudDAO<Long, NotebookUser> {
    AllNotebooksDTO getAllNotebooksByUserId(Long userId);
}
