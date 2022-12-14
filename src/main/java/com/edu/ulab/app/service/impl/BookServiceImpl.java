package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.repository.BookRepository;
import com.edu.ulab.app.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book newBook = bookMapper.bookDtoToBookEntity(bookDto);
        log.info("Mapped book: {}", newBook);
        Book savedBook = bookRepository.save(newBook);
        log.info("Saved book: {}", savedBook);
        return bookMapper.bookEntityToBookDto(savedBook);
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        Book updatedBook = bookRepository.findById(bookDto.getId())
                .map(book -> bookMapper.bookDtoToBookEntity(bookDto))
                .map(bookRepository::save)
                .orElseThrow(() -> new NotFoundException(String.format("Book with id = %s not found", bookDto.getId())));
        log.info("Updated book: {}", updatedBook);
        return bookMapper.bookEntityToBookDto(updatedBook);
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Book with id = %s not found", id)));
        log.info("Received book: {}", book);
        return bookMapper.bookEntityToBookDto(book);
    }

    public List<BookDto> getBooksByUserId(Long userId) {
        log.info("Received books by userId = {}", userId);
        return bookRepository.findAllBooksByUserId(userId)
                .stream()
                .map(bookMapper::bookEntityToBookDto)
                .toList();
    }


    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
        log.info("Delete book by id = {}", id);
    }

}
