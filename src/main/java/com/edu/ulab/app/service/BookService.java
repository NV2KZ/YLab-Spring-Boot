package com.edu.ulab.app.service;


import com.edu.ulab.app.dto.BookDto;

import java.util.List;

/**
 * Interface for CRUD operations for a Book.
 */
public interface BookService {
    /**
     * Create new Book in database. Generate the id and insert new Book in database.
     * @param bookDto – must not be null.
     * @return bookDto object with generated id.
     */
    BookDto createBook(BookDto userDto);

    /**
     * Update Book for given userDto in database. Update fields of an existing Book in database.
     * @param bookDto – must not be null.
     * @return bookDto object with updated fields.
     */
    BookDto updateBook(BookDto userDto);

    /**
     * Return bookDto object for given id from database.
     * @param id – must not be null.
     * @return bookDto object for given id.
     */
    BookDto getBookById(Long id);

    /**
     * Return list of bookDto object for given userId from database.
     * @param userId – must not be null.
     * @return list of bookDto objects for given userId.
     */
    List<BookDto> getBooksByUserId(Long userId);

    /**
     * Delete Book for given id from database.
     * @param id – must not be null.
     */
    void deleteBookById(Long id);
}
