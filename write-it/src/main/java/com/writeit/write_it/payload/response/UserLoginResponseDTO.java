package com.writeit.write_it.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginResponseDTO {
    private String accessToken;
    private String refreshToken;
}
