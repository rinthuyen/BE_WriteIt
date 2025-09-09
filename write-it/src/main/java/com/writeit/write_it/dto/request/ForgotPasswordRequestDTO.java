package com.writeit.write_it.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPasswordRequestDTO {
    @NotBlank(message = "{email.notBlank}")
    @Email(message="{email.invalid}")
    private String email;
}
