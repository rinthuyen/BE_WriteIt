package com.writeit.write_it.service.notebook;

import com.writeit.write_it.dto.AllNotebooksDTO;
import com.writeit.write_it.dto.NotebookSummaryDTO;
import com.writeit.write_it.dto.request.notebook_note_version.CreateANotebookRequest;

public interface NotebookService {
    AllNotebooksDTO getAllNotebooksByUserId(Long id);

    NotebookSummaryDTO createANotebook(CreateANotebookRequest request, Long userId);

    void updateANotebook(Long id, String notebookName);
}
