package com.writeit.write_it.dto;

import com.writeit.write_it.entity.enums.NotebookUserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotebookSummaryDTO {
    private Long id;
    private String name;
    private NotebookUserRole role;
}
