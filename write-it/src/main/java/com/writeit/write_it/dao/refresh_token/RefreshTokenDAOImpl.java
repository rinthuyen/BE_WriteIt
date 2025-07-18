package com.writeit.write_it.dao.refresh_token;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.writeit.write_it.entity.RefreshToken;
import jakarta.persistence.EntityManager;

@Repository
public class RefreshTokenDAOImpl implements RefreshTokenDAO {
    private EntityManager entityManager;

    public RefreshTokenDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public RefreshToken save(RefreshToken refreshToken) {
        entityManager.persist(refreshToken);
        return refreshToken;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        List<RefreshToken> refreshTokens = entityManager
                .createQuery("SELECT rf FROM RefreshToken rf WHERE rf.token = :token", RefreshToken.class)
                .setParameter("token", token)
                .getResultList();
        return refreshTokens.stream().findFirst();
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
