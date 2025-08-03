package com.writeit.write_it.dao.refresh_token;

import java.time.Instant;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.writeit.write_it.dao.crud.CrudDAOImpl;
import com.writeit.write_it.entity.RefreshToken;
import jakarta.persistence.EntityManager;

@Repository
public class RefreshTokenDAOImpl extends CrudDAOImpl<String, RefreshToken> implements RefreshTokenDAO {

    public RefreshTokenDAOImpl(EntityManager entityManager) {
        super(entityManager, RefreshToken.class);
    }

    @Override
    @Transactional
    public void revokeToken(String token) {
        RefreshToken refreshToken = entityManager.find(RefreshToken.class, token);
        if (refreshToken != null && !refreshToken.isRevoked()) {
            refreshToken.setRevoked(true);
        }
    }

    @Override
    @Transactional
    public void revokeAllTokenByUserId(Long userId) {
        entityManager.createQuery("UPDATE RefreshToken rt SET rt.isRevoked = true WHERE rt.user.id = :userId")
                .setParameter("userId", userId)
                .executeUpdate();
    }

    @Override
    @Transactional
    public void cleanUpExpiredandRevokedTokens() {
        entityManager.createQuery("DELETE FROM RefreshToken rt WHERE rt.expiresInstant < :now OR rt.isRevoked = true")
                .setParameter("now", Instant.now())
                .executeUpdate();
    }
}
