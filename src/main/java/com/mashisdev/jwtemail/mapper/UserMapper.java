package com.mashisdev.jwtemail.mapper;

import com.mashisdev.jwtemail.dto.request.auth.RegisterRequestDto;
import com.mashisdev.jwtemail.dto.response.UserDto;
import com.mashisdev.jwtemail.model.User;
import com.mashisdev.jwtemail.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // User <-> UserEntity
    UserEntity userToUserEntity(User user);
    User userEntityToUser(UserEntity userEntity);

    // User <-> UserDto
    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);

    // registerRequest -> User
    User registerRequestToUser(RegisterRequestDto registerRequestDto);
}
