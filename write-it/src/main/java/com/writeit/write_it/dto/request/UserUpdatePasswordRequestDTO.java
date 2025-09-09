package com.writeit.write_it.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdatePasswordRequestDTO {
    @NotBlank(message = "{password.notBlank}")
    @Size(min = 5, message = "{password.size}")
    private String password;
}
