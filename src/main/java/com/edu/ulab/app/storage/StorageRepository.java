package com.edu.ulab.app.storage;

import java.util.List;
import java.util.Optional;

public interface StorageRepository<E, I> {
    E save(E entity);

    Optional<E> findById(I id);

    List<E> findAll();

    void deleteById(I id);
}
