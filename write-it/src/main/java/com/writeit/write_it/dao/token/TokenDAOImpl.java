package com.writeit.write_it.dao.token;

import java.time.Instant;

import org.springframework.stereotype.Repository;

import com.writeit.write_it.dao.crud.SoftCrudDAOImpl;
import com.writeit.write_it.entity.Token;
import com.writeit.write_it.entity.enums.TokensPurpose;

import jakarta.persistence.EntityManager;

@Repository
public class TokenDAOImpl extends SoftCrudDAOImpl<String, Token> implements TokenDAO {

    public TokenDAOImpl(EntityManager entityManager) {
        super(entityManager, Token.class);
    }

    @Override
    // @Transactional
    public void revokeRefreshToken(String token) {
        Token refreshToken = entityManager.find(Token.class, token);
        if (refreshToken != null
                && refreshToken.getPurpose() == TokensPurpose.REFRESH
                && !refreshToken.isRevoked()) {
            refreshToken.setRevoked(true);
        }
    }

    @Override
    // @Transactional
    public void revokeAllRefreshTokenByUserId(Long userId) {
        String query = "UPDATE Token t SET t.revoked = true WHERE t.user.id = :userId AND t.purpose = :purpose";
        entityManager.createQuery(query)
                .setParameter("userId", userId)
                .setParameter("purpose", TokensPurpose.REFRESH)
                .executeUpdate();
    }

    @Override
    // @Transactional
    public void cleanUpExpiredAndRevokedTokens() {
        Instant now = Instant.now();
        String deleteRefreshTokenQuery = "DELETE FROM Token t WHERE t.purpose = :purpose AND (t.expiresInstant < :now OR t.revoked = true)";
        entityManager.createQuery(deleteRefreshTokenQuery)
                .setParameter("purpose", TokensPurpose.REFRESH)
                .setParameter("now", now)
                .executeUpdate();

        String deleteSingleUseTokenQuery = "DELETE FROM Token t WHERE t.purpose <> :purpose AND (t.expiresInstant < :now OR t.usedInstant IS NOT NULL)";
        entityManager.createQuery(deleteSingleUseTokenQuery)
                .setParameter("purpose", TokensPurpose.REFRESH)
                .setParameter("now", now)
                .executeUpdate();
    }

    @Override
    // @Transactional
    public boolean consumeSingleUseToken(TokensPurpose purpose, String token) {
        String query = "UPDATE Token t SET t.usedInstant = :now WHERE t.purpose = :purpose AND t.token = :token AND t.usedInstant IS NULL AND t.expiresInstant > :now";
        int result = entityManager.createQuery(query)
                .setParameter("now", Instant.now())
                .setParameter("purpose", purpose)
                .setParameter("token", token)
                .executeUpdate();
        return result == 1;
    }
}
