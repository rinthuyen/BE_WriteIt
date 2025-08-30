package com.writeit.write_it.dao.token;

import com.writeit.write_it.dao.crud.CrudDAO;
import com.writeit.write_it.entity.Token;
import com.writeit.write_it.entity.enums.TokensPurpose;

public interface TokenDAO extends CrudDAO<String, Token> {
    // soft delete a token when logging out
    void revokeRefreshToken(String token);

    // log out of all devices
    void revokeAllRefreshTokenByUserId(Long userId);

    void cleanUpExpiredAndRevokedTokens();

    boolean consumeSingleUseToken(TokensPurpose purpose, String token);
}
