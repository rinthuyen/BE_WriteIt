package com.writeit.write_it.dao.crud;

import java.util.Optional;

public interface CrudDAO<K, V> {
    V create(V object);

    Optional<V> findById(K id);

    void update(V object);
}
