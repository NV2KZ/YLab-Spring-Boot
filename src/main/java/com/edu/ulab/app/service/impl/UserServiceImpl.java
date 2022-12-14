package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.repository.UserRepository;
import com.edu.ulab.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity userNew = userMapper.userDtoToUserEntity(userDto);
        log.info("Mapped user: {}", userNew);
        UserEntity savedUser = userRepository.save(userNew);
        log.info("Saved user: {}", savedUser);
        return userMapper.userEntityToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        UserEntity updatedUser = userRepository.findById(userDto.getId())
                .map(user -> userMapper.userDtoToUserEntity(userDto))
                .map(userRepository::save)
                .orElseThrow(() -> new NotFoundException(String.format("User with id = %s not found", userDto.getId())));
        log.info("Updated user: {}", updatedUser);
        return userMapper.userEntityToUserDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id = %s not found", id)));
        log.info("Received user: {}", user);
        return userMapper.userEntityToUserDto(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
        log.info("Delete user by id = {}", id);
    }
}
