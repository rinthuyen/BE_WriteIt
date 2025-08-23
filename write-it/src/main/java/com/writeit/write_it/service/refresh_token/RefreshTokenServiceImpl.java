package com.writeit.write_it.service.refresh_token;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.writeit.write_it.common.exception.CustomException;
import com.writeit.write_it.common.exception.ExceptionMessage;
import com.writeit.write_it.dao.refresh_token.RefreshTokenDAO;
import com.writeit.write_it.entity.RefreshToken;
import com.writeit.write_it.entity.User;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${jwt.refresh-token.expiration-days}")
    private int expirationDays;

    private RefreshTokenDAO refreshTokenDAO;

    public RefreshTokenServiceImpl(RefreshTokenDAO refreshTokenDAO) {
        this.refreshTokenDAO = refreshTokenDAO;
    }

    @Override
    @Transactional
    public RefreshToken create(User user, String deviceInfo) {
        RefreshToken token = new RefreshToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiresInstant(Instant.now().plus(Duration.ofDays(expirationDays)));
        token.setDeviceInfo(deviceInfo);
        token.setRevoked(false);
        return refreshTokenDAO.create(token);
    }

    @Override
    public RefreshToken validate(String token) {
        return refreshTokenDAO.findById(token)
                .filter(rt -> !rt.isRevoked() && rt.getExpiresInstant().isAfter(Instant.now()))
                .orElseThrow(() -> new CustomException(ExceptionMessage.InvalidRefreshToken));
    }

    @Override
    @Transactional
    public void revoke(String token) {
        refreshTokenDAO.revokeToken(token);
    }

    @Override
    @Transactional
    public void revokeAllByUserId(Long userId) {
        refreshTokenDAO.revokeAllTokenByUserId(userId);
    }

}
