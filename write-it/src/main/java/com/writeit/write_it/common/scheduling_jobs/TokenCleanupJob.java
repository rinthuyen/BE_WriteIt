package com.writeit.write_it.common.scheduling_jobs;

import org.springframework.scheduling.annotation.Scheduled;

import com.writeit.write_it.dao.token.TokenDAO;

public class TokenCleanupJob {

    private TokenDAO tokenDAO;

    public TokenCleanupJob(TokenDAO tokenDAO) {
        this.tokenDAO = tokenDAO;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredandRevokedTokens() {
        tokenDAO.cleanUpExpiredAndRevokedTokens();
    }
}
