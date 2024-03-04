package org.TechnicalSupport.controllers;

import org.TechnicalSupport.dto.UserDto;
import org.TechnicalSupport.dto.factories.DtoFactory;
import org.TechnicalSupport.entity.User;
import org.TechnicalSupport.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(name = "/hello")
public class UserController {

    public static final String LIST_ALL_USERS = "/api/v1/user/";

    private final UserRepository userRepository;
    private final DtoFactory<User, UserDto> userDtoFactory;

    public UserController(UserRepository userRepository, DtoFactory<User, UserDto> userDtoFactory) {
        this.userRepository = userRepository;
        this.userDtoFactory = userDtoFactory;
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
