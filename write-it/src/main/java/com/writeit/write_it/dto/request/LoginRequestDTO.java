package com.writeit.write_it.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "{username.notBlank}")
    @Size(min = 3, max = 20, message = "{username.size}")
    private String username;
    
    @NotBlank(message = "{password.notBlank}")
    @Size(min = 5, message = "{password.size}")
    private String password;

    private String deviceInfo;
}
