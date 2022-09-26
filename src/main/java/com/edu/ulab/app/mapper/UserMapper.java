package com.edu.ulab.app.mapper;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.web.request.UserRequest;
import org.mapstruct.Mapper;

/**
 * Interface for mapping operations for a User type.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    /**
     * Map userRequest to userDto.
     * @param userRequest – must not be null.
     * @return UserDto object.
     */
    UserDto userRequestToUserDto(UserRequest userRequest);

    /**
     * Map userDto to userRequest.
     * @param userDto – must not be null.
     * @return UserRequest object.
     */
    UserRequest userDtoToUserRequest(UserDto userDto);

    /**
     * Map userEntity to userDto.
     * @param user – object of UserEntity. Must not be null.
     * @return UserDto object.
     */
    UserDto userEntityToUserDto(UserEntity user);

    /**
     * Map userDto to userEntity.
     * @param userDto – must not be null.
     * @return UserEntity object.
     */
    UserEntity userDtoToUserEntity(UserDto userDto);
}
