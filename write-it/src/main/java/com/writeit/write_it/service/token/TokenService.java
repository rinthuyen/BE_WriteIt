package com.writeit.write_it.service.token;

import com.writeit.write_it.entity.Token;
import com.writeit.write_it.entity.User;
import com.writeit.write_it.entity.enums.TokensPurpose;

public interface TokenService {
    Token createRefreshToken(User user, String deviceInfo);

    Token createSingleUseToken(User user, TokensPurpose purpose);

    Token validateRefreshToken(String token);

    Token validateSingleUseToken(String token);

    boolean consumeSingleUseToken(TokensPurpose purpose, String token);

    void revokeRefreshToken(String token);

    void revokeAllRefreshTokenByUserId(Long userId);
}
