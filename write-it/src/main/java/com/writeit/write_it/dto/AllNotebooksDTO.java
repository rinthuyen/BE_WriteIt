package com.writeit.write_it.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllNotebooksDTO {
    private List<NotebookSummaryDTO> notebooks;
}
