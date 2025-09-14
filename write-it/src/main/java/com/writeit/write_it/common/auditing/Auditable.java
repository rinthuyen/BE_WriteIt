package com.writeit.write_it.common.auditing;

import java.time.Instant;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class Auditable {
    @CreatedDate
    @Column(name="created_instant", nullable=false, updatable=false)
    private Instant createInstant;

    @LastModifiedDate
    @Column(name="updated_instant", nullable=false)
    private Instant updatedInstant;

    @CreatedBy
    @Column(name="created_by", nullable=false, updatable=false)
    private String createdBy;

    @LastModifiedBy
    @Column(name="updated_by", nullable=false)
    private String updatedBy;
}
