package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.User;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserStorage implements StorageRepository<User, Long> {

    private final Map<Long, User> storage = new ConcurrentHashMap<>();

    @Override
    public User save(User entity) {
        storage.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<User> findAll() {
        return storage.values().stream().toList();
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }
}



