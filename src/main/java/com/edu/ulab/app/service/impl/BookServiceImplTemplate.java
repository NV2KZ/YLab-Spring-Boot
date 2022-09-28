package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImplTemplate implements BookService {

    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT_SQL = "INSERT INTO BOOK(TITLE, AUTHOR, PAGE_COUNT, USER_ID) VALUES (?,?,?,?)";
    private static final String UPDATE_SQL = "UPDATE BOOK SET TITLE=?, AUTHOR=?, PAGE_COUNT=?, USER_ID=? WHERE ID=?";
    private static final String SELECT_SQL = "SELECT * FROM BOOK WHERE ID=?";
    private static final String SELECT_SQL_BY_USER_ID = "SELECT * FROM BOOK WHERE USER_ID=?";
    private static final String DELETE_SQL = "DELETE FROM BOOK WHERE id=?";

    @Override
    public BookDto createBook(BookDto bookDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                    ps.setString(1, bookDto.getTitle());
                    ps.setString(2, bookDto.getAuthor());
                    ps.setLong(3, bookDto.getPageCount());
                    ps.setLong(4, bookDto.getUserId());
                    return ps;
                },
                keyHolder);

        bookDto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        log.info("Created book: {}", bookDto);
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        jdbcTemplate.update(UPDATE_SQL,
                bookDto.getTitle(),
                bookDto.getAuthor(),
                bookDto.getPageCount(),
                bookDto.getUserId(),
                bookDto.getId());
        log.info("Updated book: {}", bookDto);
        return bookDto;
    }

    @Override
    public BookDto getBookById(Long id) {
        log.info("Received book by id = {}", id);
        return jdbcTemplate.query(SELECT_SQL, DataClassRowMapper.newInstance(BookDto.class), id)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException(String.format("Book with id = %s not found", id)));
    }

    @Override
    public List<BookDto> getBooksByUserId(Long userId) {
        log.info("Received book by userId = {}", userId);
        return jdbcTemplate.query(SELECT_SQL_BY_USER_ID, DataClassRowMapper.newInstance(BookDto.class), userId);
    }

    @Override
    public void deleteBookById(Long id) {
        jdbcTemplate.update(DELETE_SQL, id);
        log.info("Delete book by id = {}", id);
    }
}
