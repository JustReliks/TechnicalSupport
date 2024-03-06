package org.TechnicalSupport.controllers;

import lombok.RequiredArgsConstructor;
import org.TechnicalSupport.dto.UserLoginDto;
import org.TechnicalSupport.dto.UserLoginRequestDto;
import org.TechnicalSupport.dto.factories.DtoFactory;
import org.TechnicalSupport.exception.UserAlreadyExistsException;
import org.TechnicalSupport.repository.UserRepository;
import org.TechnicalSupport.security.JwtUserDetails;
import org.TechnicalSupport.service.LoginService;
import org.TechnicalSupport.service.RegisterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    public static final String LOGIN_USER = "/api/v1/user/login";
    public static final String REGISTER_USER = "/api/v1/user/register";
    public static final String LOGOUT_USER = "/api/v1/user/logout";

    private final DtoFactory<JwtUserDetails, UserLoginDto> userLoginFactory;
    private final LoginService loginService;
    private final RegisterService registerService;
    private final UserRepository userRepository;

    @PostMapping(LOGIN_USER)
    public UserLoginDto login(@RequestBody UserLoginRequestDto userLogin) {
        JwtUserDetails login = loginService.login(userLogin);
        return userLoginFactory.toDto(login);
    }

    @PostMapping(REGISTER_USER)
    public Long register(@RequestBody UserLoginRequestDto userLogin) {
        if (userRepository.findUserByUsername(userLogin.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User with name %s already exists!".formatted(userLogin.getUsername()));
        }
        return registerService.register(userLogin.getUsername(), userLogin.getPassword());
    }


}
