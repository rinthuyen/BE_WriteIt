package com.writeit.write_it.service.refresh_token;

import com.writeit.write_it.entity.RefreshToken;
import com.writeit.write_it.entity.User;

public interface RefreshTokenService {
    RefreshToken create(User user, String deviceInfo);

    RefreshToken validate(String token);

    void revoke(String token);

    void revokeAllByUserId(Long userId);
}
