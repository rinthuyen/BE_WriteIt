package com.writeit.write_it.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthTokenResponseDTO {
    private String accessToken;
    private String refreshToken;
}
