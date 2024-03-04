package org.TechnicalSupport.controllers;

import lombok.RequiredArgsConstructor;
import org.TechnicalSupport.dto.UserDto;
import org.TechnicalSupport.dto.UserLoginDto;
import org.TechnicalSupport.dto.UserLoginRequestDto;
import org.TechnicalSupport.dto.factories.DtoFactory;
import org.TechnicalSupport.entity.User;
import org.TechnicalSupport.repository.UserRepository;
import org.TechnicalSupport.security.JwtUserDetails;
import org.TechnicalSupport.service.LoginService;
import org.TechnicalSupport.service.RegisterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

    public static final String LIST_ALL_USERS = "/api/v1/user";
    public static final String LOGIN_USER = "/api/v1/user/login";
    public static final String REGISTER_USER = "/api/v1/user/register";

    private final UserRepository userRepository;
    private final DtoFactory<User, UserDto> userDtoFactory;
    private final DtoFactory<JwtUserDetails, UserLoginDto> userLoginFactory;
    private final LoginService loginService;
    private final RegisterService registerService;

    @PostMapping(LOGIN_USER)
    public UserLoginDto login(@RequestBody UserLoginRequestDto userLogin) {
        JwtUserDetails login = loginService.login(userLogin);
        return userLoginFactory.toDto(login);
    }

    @PostMapping(REGISTER_USER)
    public Long register(@RequestBody UserLoginRequestDto userLogin) {
        return registerService.register(userLogin.getUsername(), userLogin.getPassword());
    }

    @GetMapping(LIST_ALL_USERS)
    public List<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userDtoFactory::toDto)
                .collect(Collectors.toList());
    }
}
