package com.writeit.write_it.dto.request;

import com.writeit.write_it.common.custom_annotations.EnumValue;
import com.writeit.write_it.entity.enums.Status;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdateRequestDTO {
    @NotBlank(message = "Displayed name cannot be empty")
    private String displayedName;
    @Email
    private String email;
    @EnumValue
    @NotNull(message = "Status cannot be null")
    private Status status;
}
