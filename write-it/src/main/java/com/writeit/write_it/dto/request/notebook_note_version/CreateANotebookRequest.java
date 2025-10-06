package com.writeit.write_it.dto.request.notebook_note_version;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateANotebookRequest {
    @NotBlank(message = "{notebook.name.notBlank}")
    private String name;
}
