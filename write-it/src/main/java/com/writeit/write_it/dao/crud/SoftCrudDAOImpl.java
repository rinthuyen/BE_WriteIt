package com.writeit.write_it.dao.crud;

import java.time.Instant;

import org.springframework.transaction.annotation.Transactional;

import com.writeit.write_it.common.auditing.AuditableAndSoftDeletable;

import jakarta.persistence.EntityManager;

public class SoftCrudDAOImpl<K, V extends AuditableAndSoftDeletable> extends CrudDAOImpl<K, V> implements SoftCrudDAO<K, V> {
    public SoftCrudDAOImpl(EntityManager entityManager, Class<V> entityClass) {
        super(entityManager, entityClass);
    }
    @Override 
    @Transactional
    public int deleteById(K id) {
        V object = entityManager.find(entityClass, id);
        if (object == null) return -1;
        if (object.isDeleted()) return 0;
        object.setDeletedInstant(Instant.now());
        entityManager.merge(object);
        return 1;
    }
    @Override
    @Transactional
    public int restoreById(K id) {
        V object = entityManager.find(entityClass, id);
        if (object == null) return -1;
        if (!object.isDeleted()) return 0;
        object.setDeletedInstant(null);
        entityManager.merge(object);
        return 1;
    }

}
