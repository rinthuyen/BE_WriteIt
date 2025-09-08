package com.writeit.write_it.service.token;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.writeit.write_it.common.exception.AppException;
import com.writeit.write_it.common.exception.ExceptionMessage;
import com.writeit.write_it.dao.token.TokenDAO;
import com.writeit.write_it.entity.Token;
import com.writeit.write_it.entity.User;
import com.writeit.write_it.entity.enums.TokensPurpose;

@Service
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
            throw new AppException(ExceptionMessage.INVALID_TOKEN_PURPOSE);
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
    public Token validateRefreshToken(String token) {
        return tokenDAO.findById(token)
                .filter(t -> t.getPurpose() == TokensPurpose.REFRESH)
                .filter(t -> !t.isRevoked())
                .filter(t -> t.getExpiresInstant().isAfter(Instant.now()))
                .orElseThrow(() -> new AppException(ExceptionMessage.INVALID_REFRESH_TOKEN));
    }

    @Override
    public Token validateSingleUseToken(String token) {
        return tokenDAO.findById(token)
                .filter(t -> t.getPurpose() != TokensPurpose.REFRESH)
                .filter(t -> t.getExpiresInstant().isAfter(Instant.now()))
                .filter(t -> t.getUsedInstant() == null)
                .orElseThrow(() -> new AppException(ExceptionMessage.INVALID_SINGLE_USE_TOKEN));
    }

    @Override
    @Transactional
    public boolean consumeSingleUseToken(TokensPurpose purpose, String token) {
        if (purpose == TokensPurpose.REFRESH) {
            throw new AppException(ExceptionMessage.INVALID_TOKEN_PURPOSE);
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
