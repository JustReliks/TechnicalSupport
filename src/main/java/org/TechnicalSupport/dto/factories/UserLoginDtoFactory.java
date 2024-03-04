package org.TechnicalSupport.dto.factories;

import org.TechnicalSupport.dto.UserLoginDto;
import org.TechnicalSupport.security.JwtUserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserLoginDtoFactory implements DtoFactory<JwtUserDetails, UserLoginDto> {
    @Override
    public UserLoginDto toDto(JwtUserDetails jwtUserDetails) {
        return UserLoginDto.builder()
                .token(jwtUserDetails.getToken())
                .username(jwtUserDetails.getUsername())
                .build();
    }
}
