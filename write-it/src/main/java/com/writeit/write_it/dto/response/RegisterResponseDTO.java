package com.writeit.write_it.dto.response;

import com.writeit.write_it.entity.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponseDTO {
    private String displayedName;
    private Status status;
}
