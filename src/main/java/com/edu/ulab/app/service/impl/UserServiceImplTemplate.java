package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImplTemplate implements UserService {

    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT_SQL = "INSERT INTO PERSON(FULL_NAME, TITLE, AGE) VALUES (?,?,?)";
    private static final String UPDATE_SQL = "UPDATE PERSON SET FULL_NAME=?, TITLE=?, AGE=? WHERE ID=?";
    private static final String SELECT_SQL = "SELECT * FROM PERSON WHERE ID=?";
    private static final String DELETE_SQL = "DELETE FROM PERSON WHERE ID=?";

    @Override
    public UserDto createUser(UserDto userDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                    ps.setString(1, userDto.getFullName());
                    ps.setString(2, userDto.getTitle());
                    ps.setLong(3, userDto.getAge());
                    return ps;
                }, keyHolder);

        userDto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        log.info("Created user: {}", userDto);
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        jdbcTemplate.update(UPDATE_SQL,
                userDto.getFullName(),
                userDto.getTitle(),
                userDto.getAge(),
                userDto.getId());
        log.info("Updated user: {}", userDto);
        return userDto;

    }

    @Override
    public UserDto getUserById(Long id) {
        return jdbcTemplate.query(SELECT_SQL, DataClassRowMapper.newInstance(UserDto.class), id)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException(String.format("User with id = %s not found", id)));
    }

    @Override
    public void deleteUserById(Long id) {
        jdbcTemplate.update(DELETE_SQL, id);
        log.info("Delete user by id={}", id);
    }
}
