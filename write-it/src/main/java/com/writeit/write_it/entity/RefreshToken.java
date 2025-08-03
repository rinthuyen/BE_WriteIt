package com.writeit.write_it.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @Column(name = "token")
    private String token;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "expires_instant", nullable = false)
    private Instant expiresInstant;

    @Column(name = "revoked", nullable = false)
    private boolean revoked;

    // for log in multiple devices?
    @Column(name = "device_info")
    private String deviceInfo;
}
