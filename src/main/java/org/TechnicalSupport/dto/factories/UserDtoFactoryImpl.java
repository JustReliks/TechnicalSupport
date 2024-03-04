package org.TechnicalSupport.dto.factories;

import org.TechnicalSupport.dto.UserDto;
import org.TechnicalSupport.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserDtoFactoryImpl implements DtoFactory<User, UserDto> {

    @Override
    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(user.getRoles()
                        .stream()
                        .map(role -> role.getRoleEnum().name())
                        .collect(Collectors.toSet()))
                .build();
    }

}
