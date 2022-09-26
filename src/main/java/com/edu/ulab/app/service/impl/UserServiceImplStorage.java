package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImplStorage implements UserService {

    private final UserStorage userStorage;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity userNew = userMapper.userDtoToUserEntity(userDto);
        log.info("Mapped user: {}", userNew);
        UserEntity savedUser = userStorage.save(userNew);
        log.info("Saved user: {}", savedUser);
        return userMapper.userEntityToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        UserEntity updatedUser = userStorage.findById(userDto.getId())
                .map(user -> userMapper.userDtoToUserEntity(userDto))
                .map(userStorage::save)
                .orElseThrow(() -> new NotFoundException(String.format("User with id = %s not found", userDto.getId())));
        log.info("Updated user: {}", updatedUser);
        return userMapper.userEntityToUserDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Long id) {
        UserEntity user = userStorage.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id = %s not found", id)));
        log.info("Received user: {}", user);
        return userMapper.userEntityToUserDto(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userStorage.deleteById(id);
        log.info("Delete book by id = {}", id);
    }
}
