package org.TechnicalSupport.service.impl;

import lombok.RequiredArgsConstructor;
import org.TechnicalSupport.dto.UserLoginRequestDto;
import org.TechnicalSupport.security.JwtUserDetails;
import org.TechnicalSupport.service.LoginService;
import org.TechnicalSupport.service.UserService;
import org.TechnicalSupport.service.security.UserDetailsServiceImpl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserService userService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public JwtUserDetails login(UserLoginRequestDto loginRequestDto) {
        // String encoded = Arrays.toString(Base64.getDecoder().decode(loginRequestDto.getPassword()));
        return userService.authenticate(loginRequestDto.getUsername(), loginRequestDto.getPassword());
    }

    @Override
    public JwtUserDetails getCurrentUser() {
        return userDetailsService.getCurrentUser();
    }
}
