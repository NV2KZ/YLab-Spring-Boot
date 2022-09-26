package com.edu.ulab.app.repository;

import com.edu.ulab.app.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Interface for generic CRUD operations on a repository for a Book type.
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Return list of Book object for given userId from database.
     * @param userId – must not be null.
     * @return list of Book objects for given userId.
     */
    @Query(value = "select b from Book b where b.userId = ?1")
    List<Book> findAllBooksByUserId(Long userId);
}
