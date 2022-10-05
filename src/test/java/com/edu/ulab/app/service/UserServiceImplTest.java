package com.edu.ulab.app.service;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.repository.UserRepository;
import com.edu.ulab.app.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Тестирование функционала {@link UserServiceImpl}.
 */
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DisplayName("Testing user functionality.")
public class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Test
    @DisplayName("Создание пользователя. Должно пройти успешно.")
    void createUser_Test() {
        //given

        UserDto userDto = new UserDto();
        userDto.setAge(11);
        userDto.setFullName("test name");
        userDto.setTitle("test title");

        UserEntity user = new UserEntity();
        user.setFullName("test name");
        user.setAge(11);
        user.setTitle("test title");

        UserEntity savedUser = new UserEntity();
        savedUser.setId(1L);
        savedUser.setFullName("test name");
        savedUser.setAge(11);
        savedUser.setTitle("test title");

        UserDto result = new UserDto();
        result.setId(1L);
        result.setAge(11);
        result.setFullName("test name");
        result.setTitle("test title");

        //when

        when(userMapper.userDtoToUserEntity(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.userEntityToUserDto(savedUser)).thenReturn(result);

        //then

        UserDto userDtoResult = userService.createUser(userDto);
        assertEquals(1L, userDtoResult.getId());
        assertEquals("test title", userDtoResult.getTitle());
        assertEquals("test name", userDtoResult.getFullName());
    }

    @Test
    @DisplayName("Обновление пользователя. Должно пройти успешно.")
    void updateUser_Test() {
        //given
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setAge(11);
        userDto.setFullName("test update name");
        userDto.setTitle("test update title");

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setFullName("test name");
        user.setAge(11);
        user.setTitle("test title");

        UserEntity updatedUser = new UserEntity();
        updatedUser.setId(1L);
        updatedUser.setFullName("test update name");
        updatedUser.setAge(11);
        updatedUser.setTitle("test update title");

        UserDto result = new UserDto();
        result.setId(1L);
        result.setAge(11);
        result.setFullName("test update name");
        result.setTitle("test update title");

        //when
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.userDtoToUserEntity(userDto)).thenReturn(updatedUser);
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);
        when(userMapper.userEntityToUserDto(updatedUser)).thenReturn(result);

        //then

        UserDto userDtoResult = userService.updateUser(userDto);
        assertEquals(1L, userDtoResult.getId());
        assertEquals("test update title", userDtoResult.getTitle());
        assertEquals("test update name", userDtoResult.getFullName());
    }

    @Test
    @DisplayName("Получение пользователя по id. Должно пройти успешно.")
    void getUserById_Test() {
        //given
        Long id = 1L;

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setFullName("test name");
        user.setAge(11);
        user.setTitle("test title");

        Optional<UserEntity> receivedUser = Optional.of(user);

        UserDto result = new UserDto();
        result.setId(1L);
        result.setAge(11);
        result.setFullName("test name");
        result.setTitle("test title");

        //when

        when(userRepository.findById(id)).thenReturn(receivedUser);
        when(userMapper.userEntityToUserDto(receivedUser.get())).thenReturn(result);

        //then

        UserDto userDtoResult = userService.getUserById(id);
        assertEquals(1L, userDtoResult.getId());
        assertEquals("test title", userDtoResult.getTitle());
        assertEquals("test name", userDtoResult.getFullName());
    }

    @Test
    @DisplayName("Удаление пользователя. Должно пройти успешно.")
    void deleteUser_Test() {

        Long userId = 1L;

        userRepository.deleteById(userId);

        verify(userRepository, times(1)).deleteById(eq(userId));
    }
}
