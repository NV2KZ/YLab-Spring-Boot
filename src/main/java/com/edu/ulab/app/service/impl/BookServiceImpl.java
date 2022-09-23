package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.storage.StorageRepository;
import com.edu.ulab.app.utils.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final StorageRepository<Book, Long> bookStorage;
    private final BookMapper bookMapper;

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book newBook = Book.builder()
                .id(IdGenerator.nextId())
                .userId(bookDto.getUserId())
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .pageCount(bookDto.getPageCount())
                .build();
        Book savedBook = bookStorage.save(newBook);
        return bookMapper.bookEntityToBookDto(savedBook);
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        Book updatedBook = bookStorage.findById(bookDto.getId())
                .map(book -> Book.builder()
                        .id(bookDto.getId())
                        .userId(bookDto.getUserId())
                        .title(bookDto.getTitle())
                        .author(bookDto.getAuthor())
                        .pageCount(bookDto.getPageCount())
                        .build())
                .map(bookStorage::save)
                .orElseThrow(() -> new NotFoundException(String.format("Book with id = %s not found", bookDto.getId())));
        return bookMapper.bookEntityToBookDto(updatedBook);
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookStorage.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Book with id = %s not found", id)));
        return bookMapper.bookEntityToBookDto(book);
    }

    public List<BookDto> getBooksByUserId(Long userId) {
        return bookStorage.findAll()
                .stream()
                .filter(book -> book.getUserId().equals(userId))
                .map(bookMapper::bookEntityToBookDto)
                .toList();
    }


    @Override
    public void deleteBookById(Long id) {
        bookStorage.deleteById(id);
    }
}
