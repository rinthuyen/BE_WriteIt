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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notebook_note", uniqueConstraints = {
        @UniqueConstraint(name = "notebook_user_active", columnNames = { "notebook_id", "added_by", "active" })
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("deleted_instant IS NULL")
public class NotebookNote extends AuditableAndSoftDeletable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "notebook_id", referencedColumnName = "id", nullable = false)
    private Notebook notebook;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "note_id", referencedColumnName = "id", nullable = false)
    private Note note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "added_by")
    private User addedBy;

    @Column(name = "active", insertable = false, updatable = false, columnDefinition = "TINYINT(1) GENERATED ALWAYS AS (deleted_instant IS NULL) STORED")
    private Boolean active;
}
