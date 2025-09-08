package com.writeit.write_it.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RefreshRequestDTO {
    @NotBlank(message = "Refresh token cannot be empty")
    private String refreshToken;
}
