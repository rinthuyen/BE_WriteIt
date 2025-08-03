package com.writeit.write_it.dao.refresh_token;

import com.writeit.write_it.dao.crud.CrudDAO;
import com.writeit.write_it.entity.RefreshToken;

public interface RefreshTokenDAO extends CrudDAO<String, RefreshToken> {
    RefreshToken save(RefreshToken refreshToken);

    // soft delete a token when logging out
    void revokeToken(String token);

    // log out of all devices
    void revokeAllTokenByUserId(Long userId);

    void cleanUpExpiredandRevokedTokens();
}
