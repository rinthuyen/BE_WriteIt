package com.writeit.write_it.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRefreshRequestDTO {
    @NotBlank(message = "Refresh token is required")
    private String refreshToken;
}
