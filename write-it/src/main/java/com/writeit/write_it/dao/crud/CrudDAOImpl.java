package com.writeit.write_it.dao.crud;

import java.util.Optional;

import jakarta.persistence.EntityManager;

public class CrudDAOImpl<K, V> implements CrudDAO<K, V> {

    protected EntityManager entityManager;
    protected Class<V> entityClass;

    public CrudDAOImpl(EntityManager entityManager, Class<V> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    @Override
    // @Transactional
    public V create(V object) {
        entityManager.persist(object);
        return object;
    }

    @Override
    public Optional<V> findById(K id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    // @Transactional
    public void update(V object) {
        entityManager.merge(object);
    }
}