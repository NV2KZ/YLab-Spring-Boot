package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.StorageRepository;
import com.edu.ulab.app.utils.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final StorageRepository<User, Long> userStorage;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User userNew = User.builder()
                .id(IdGenerator.nextId())
                .fullName(userDto.getFullName())
                .title(userDto.getTitle())
                .age(userDto.getAge())
                .build();
        User savedUser = userStorage.save(userNew);
        return userMapper.userEntityToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User updatedUser = userStorage.findById(userDto.getId())
                .map(user -> User.builder()
                        .id(userDto.getId())
                        .fullName(userDto.getFullName())
                        .title(userDto.getTitle())
                        .age(userDto.getAge())
                        .build())
                .map(userStorage::save)
                .orElseThrow(() -> new NotFoundException(String.format("User with id = %s not found", userDto.getId())));
        return userMapper.userEntityToUserDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userStorage.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id = %s not found", id)));
        return userMapper.userEntityToUserDto(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userStorage.deleteById(id);
    }
}
