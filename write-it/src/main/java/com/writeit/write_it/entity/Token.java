package com.writeit.write_it.entity;

import java.time.Instant;

import org.hibernate.annotations.SQLRestriction;

import com.writeit.write_it.common.auditing.AuditableAndSoftDeletable;
import com.writeit.write_it.entity.enums.TokensPurpose;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@SQLRestriction("deleted_instant IS NULL")
@Table(name = "token")
public class Token extends AuditableAndSoftDeletable {

    @Id
    @Column(name = "token")
    private String token;

    @Column(name = "purpose", nullable = false)
    @Enumerated(EnumType.STRING)
    private TokensPurpose purpose;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "expires_instant", nullable = false)
    private Instant expiresInstant;

    // for session
    @Column(name = "revoked", nullable = false)
    private boolean revoked;

    // for log in multiple devices?
    @Column(name = "device_info")
    private String deviceInfo;

    // for single flow (reset password)
    @Column(name = "used_instant")
    private Instant usedInstant;
}
