package com.writeit.write_it.dto.request;

import com.writeit.write_it.common.annotations.EnumValue;
import com.writeit.write_it.entity.enums.Status;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdateRequestDTO {
    @NotBlank(message = "{displayed_name.notBlank}")
    private String displayedName;

    @NotBlank(message = "{email.notBlank}")
    @Email(message="{email.invalid}")
    private String email;
    
    @EnumValue(message="{enum_value.invalid}")
    @NotNull(message = "{status.notNull}")
    private Status status;
}
