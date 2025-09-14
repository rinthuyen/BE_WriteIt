package com.writeit.write_it.service.token;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.writeit.write_it.common.exception.ApiError;
import com.writeit.write_it.common.exception.AppException;
import com.writeit.write_it.dao.token.TokenDAO;
import com.writeit.write_it.entity.Token;
import com.writeit.write_it.entity.User;
import com.writeit.write_it.entity.enums.TokensPurpose;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

    @Value("${token.ttl.refresh.days}")
    private int refreshExpirationDays;

    @Value("${token.ttl.password-reset.mins}")
    private int passwordResetExpirationMins;

    private final TokenDAO tokenDAO;

    public TokenServiceImpl(TokenDAO tokenDAO) {
        this.tokenDAO = tokenDAO;
    }

    @Override
    @Transactional
    public Token createRefreshToken(User user, String deviceInfo) {
        Instant now = Instant.now();
        Token token = new Token();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setPurpose(TokensPurpose.REFRESH);
        token.setExpiresInstant(now.plus(Duration.ofDays(refreshExpirationDays)));
        token.setDeviceInfo(deviceInfo);
        token.setRevoked(false);
        return tokenDAO.create(token);
    }

    @Override
    @Transactional
    public Token createSingleUseToken(User user, TokensPurpose purpose) {
        if (purpose == TokensPurpose.REFRESH) {
            throw new AppException(ApiError.TOKEN_PURPOSE_INVALID);
        }
        Instant now = Instant.now();
        Token token = new Token();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setPurpose(purpose);
        token.setExpiresInstant(now.plus(Duration.ofMinutes(passwordResetExpirationMins)));
        token.setRevoked(false);
        return tokenDAO.create(token);
    }


    @Override
    public Token validateRefreshToken(String tokenValue) {
        try {
            Token t = tokenDAO.findById(tokenValue)
                    .orElseThrow(() -> new IllegalStateException("not_found"));

            if (t.getPurpose() != TokensPurpose.REFRESH)  throw new IllegalStateException("wrong_purpose");
            if (t.isRevoked())                            throw new IllegalStateException("revoked");
            if (!t.getExpiresInstant().isAfter(Instant.now()))
                                                        throw new IllegalStateException("expired");
            return t;

        } catch (IllegalStateException e) {
            log.debug("refresh_token_validation_failed",
                Map.of("reason", e.getMessage(), "tokenId", tokenValue));
            throw new AppException(ApiError.REFRESH_TOKEN_INVALID);
        }
    }


    @Override
    public Token validateSingleUseToken(String tokenValue, TokensPurpose expectedPurpose) {
        try {
            Token t = tokenDAO.findById(tokenValue)
                    .orElseThrow(() -> new IllegalStateException("not_found"));

            if (t.getPurpose() != expectedPurpose)                  throw new IllegalStateException("wrong_purpose");
            if (t.isRevoked())                                      throw new IllegalStateException("revoked");
            if (!t.getExpiresInstant().isAfter(Instant.now()))      throw new IllegalStateException("expired");
            if (t.getUsedInstant() != null)                         throw new IllegalStateException("used");

            return t;

        } catch (IllegalStateException e) {
            log.debug("single_use_token_validation_failed",
                    Map.of("reason", e.getMessage(),
                           "expectedPurpose", String.valueOf(expectedPurpose),
                           "tokenId", tokenValue));
            throw new AppException(ApiError.SINGLE_USE_TOKEN_INVALID); // collapse externally
        }
    }

    @Override
    @Transactional
    public boolean consumeSingleUseToken(TokensPurpose purpose, String token) {
        if (purpose == TokensPurpose.REFRESH) {
            throw new AppException(ApiError.TOKEN_PURPOSE_INVALID);
        }
        return tokenDAO.consumeSingleUseToken(purpose, token);
    }

    @Override
    @Transactional
    public void revokeRefreshToken(String token) {
        tokenDAO.revokeRefreshToken(token);
    }

    @Override
    @Transactional
    public void revokeAllRefreshTokenByUserId(Long userId) {
        tokenDAO.revokeAllRefreshTokenByUserId(userId);
    }

}
