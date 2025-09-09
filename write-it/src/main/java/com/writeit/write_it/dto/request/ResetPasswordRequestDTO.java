package com.writeit.write_it.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordRequestDTO {
    @NotBlank(message = "{token.notBlank}")
    private String token;
    
    @NotBlank(message = "{password.notBlank}")
    @Size(min = 5, message = "{password.size}")
    private String password;
}
