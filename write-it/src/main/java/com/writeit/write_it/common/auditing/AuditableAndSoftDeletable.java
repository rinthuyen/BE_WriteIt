package com.writeit.write_it.common.auditing;

import java.beans.Transient;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class AuditableAndSoftDeletable extends Auditable {
    @Column(name="deleted_instant")
    private Instant deletedInstant;

    @Transient
    public boolean isDeleted() {
        return deletedInstant != null;
    }
}
