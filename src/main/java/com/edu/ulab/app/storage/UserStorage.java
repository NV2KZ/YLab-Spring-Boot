package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserStorage implements StorageRepository<UserEntity, Long> {

    private final Map<Long, UserEntity> storage = new ConcurrentHashMap<>();

    @Override
    public UserEntity save(UserEntity entity) {
        storage.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<UserEntity> findAll() {
        return storage.values().stream().toList();
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }
}



