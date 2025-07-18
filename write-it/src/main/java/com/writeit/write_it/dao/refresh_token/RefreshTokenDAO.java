package com.writeit.write_it.dao.refresh_token;

import java.util.Optional;

import com.writeit.write_it.entity.RefreshToken;

public interface RefreshTokenDAO {
    RefreshToken save(RefreshToken refreshToken);

    Optional<RefreshToken> findByToken(String token);

    // soft delete a token when logging out
    void revokeToken(String token);

    // log out of all devices
    void revokeAllTokenByUserId(Long userId);

    void cleanUpExpiredandRevokedTokens();
}
