package com.writeit.write_it.entity;

import org.hibernate.annotations.SQLRestriction;

import com.writeit.write_it.common.auditing.AuditableAndSoftDeletable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "note")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("deleted_instant IS NULL")
public class Note extends AuditableAndSoftDeletable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private User owner;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "latest_version_id", referencedColumnName = "id", nullable = false)
    private NoteVersion latestNoteVersion;
}
