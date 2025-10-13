package com.writeit.write_it.service.notebook;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.writeit.write_it.common.exception.ApiError;
import com.writeit.write_it.common.exception.AppException;
import com.writeit.write_it.dao.notebook.NotebookDAO;
import com.writeit.write_it.dao.notebook_user.NotebookUserDAO;
import com.writeit.write_it.dao.user.UserDAO;
import com.writeit.write_it.dto.AllNotebooksDTO;
import com.writeit.write_it.dto.NotebookSummaryDTO;
import com.writeit.write_it.dto.request.notebook_note_version.CreateANotebookRequest;
import com.writeit.write_it.entity.Notebook;
import com.writeit.write_it.entity.NotebookUser;
import com.writeit.write_it.entity.User;
import com.writeit.write_it.entity.enums.NotebookUserRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotebookServiceImpl implements NotebookService {
    private final NotebookDAO notebookDAO;
    private final UserDAO userDAO;
    private final NotebookUserDAO notebookUserDAO;

    @Override
    @Transactional
    public AllNotebooksDTO getAllNotebooksByUserId(Long userId) {
        return notebookUserDAO.getAllNotebooksByUserId(userId);
    }

    @Override
    @Transactional
    public NotebookSummaryDTO createANotebook(CreateANotebookRequest request, Long userId) {
        User user = userDAO
                .findById(userId)
                .orElseThrow(() -> new AppException(ApiError.USER_NOT_FOUND));
        Notebook notebook = new Notebook();
        notebook.setName(request.getName());
        notebookDAO.create(notebook);

        NotebookUser notebookUser = new NotebookUser();
        notebookUser.setRole(NotebookUserRole.OWNER);
        notebookUser.setNotebook(notebook);
        notebookUser.setUser(user);
        notebookUserDAO.create(notebookUser);
        return new NotebookSummaryDTO(notebook.getId(), notebook.getName(), notebookUser.getRole());
    }

    @Override
    public void updateANotebook(Long id, String notebookName) {
        Notebook notebook = notebookDAO.findById(id).orElseThrow(() -> new AppException(ApiError.NOTEBOOK_NOT_FOUND));
        notebook.setName(notebookName);
        notebookDAO.update(notebook);
    }
}
