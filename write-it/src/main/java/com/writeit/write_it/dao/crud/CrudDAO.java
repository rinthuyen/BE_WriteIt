package com.writeit.write_it.dao.crud;

import java.util.Optional;

public interface CrudDAO<K, V> {
    V save(V object);

    Optional<V> findById(K id);
}
