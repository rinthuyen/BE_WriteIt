package com.writeit.write_it.common.scheduling_jobs;

import org.springframework.scheduling.annotation.Scheduled;

import com.writeit.write_it.dao.refresh_token.RefreshTokenDAO;

public class RefreshTokenCleanupJob {

    private RefreshTokenDAO refreshTokenDAO;

    public RefreshTokenCleanupJob(RefreshTokenDAO refreshTokenDAO) {
        this.refreshTokenDAO = refreshTokenDAO;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredandRevokedTokens() {
        refreshTokenDAO.cleanUpExpiredandRevokedTokens();
    }
}
