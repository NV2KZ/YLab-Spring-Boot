package com.edu.ulab.app.repository;

import com.edu.ulab.app.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for generic CRUD operations on a repository for a User type.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
