package com.writeit.write_it.dao.crud;

import com.writeit.write_it.common.auditing.AuditableAndSoftDeletable;

public interface SoftCrudDAO<K, V extends AuditableAndSoftDeletable> extends CrudDAO<K, V> {
    @Override
    int deleteById(K id);

    int restoreById(K id);

    // Optional<V> findByIdIncludingDeleted(K id);
}
