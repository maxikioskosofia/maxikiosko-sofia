package com.ecommerce.ecommerce.mappers.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ecommerce.ecommerce.domain.User;
import com.ecommerce.ecommerce.dto.user.UserDto;
import com.ecommerce.ecommerce.dto.user.UserRegisterDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id_user", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(source = "name", target = "name") // Mapear name en lugar de username
    User userRegisterDtoToUser(UserRegisterDto userRegisterDto);

    @Mapping(source = "name", target = "name") // Mapear name en lugar de username // Especifica que el campo username debe mapearse directamente
    UserDto userToUserDto(User user);
}
