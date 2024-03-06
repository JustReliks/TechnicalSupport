package org.TechnicalSupport.controllers;

import lombok.RequiredArgsConstructor;
import org.TechnicalSupport.dto.RequestDto;
import org.TechnicalSupport.dto.UserDto;
import org.TechnicalSupport.dto.factories.DtoFactory;
import org.TechnicalSupport.entity.User;
import org.TechnicalSupport.entity.enums.UserRole;
import org.TechnicalSupport.exception.RoleAlreadySetException;
import org.TechnicalSupport.exception.UserNotFoundException;
import org.TechnicalSupport.repository.UserRepository;
import org.TechnicalSupport.service.AdministratorService;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AdministratorController {

    private final DtoFactory<User, UserDto> userDtoFactory;

    public static final String FETCH_REQUESTS = "/api/v1/administrator/requests/{page}";
    public static final String LIST_ALL_USERS = "/api/v1/administrator/users";
    public static final String CREATE_OPERATOR = "/api/v1/administrator/users/setOperator/{id}";

    private final AdministratorService administratorService;
    private final UserRepository userRepository;

    @GetMapping(LIST_ALL_USERS)
    public List<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userDtoFactory::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping(CREATE_OPERATOR)
    public Long setOperator(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id %s not found!".formatted(id)));

        if (user.getRoles().stream().anyMatch(role -> role.getRoleEnum().equals(UserRole.OPERATOR))) {
            throw new RoleAlreadySetException("User with id %s already operator".formatted(id));
        }
        return administratorService.setUserRole(user, UserRole.OPERATOR);
    }

    @GetMapping(FETCH_REQUESTS)
    public List<RequestDto> fetchRequests(@PathVariable int page,
                                          @RequestParam(defaultValue = "ASC") String direction,
                                          @RequestParam(required = false, name = "name") String nameFilter,
                                          @RequestParam(required = false) String status) {
        Sort.Direction dir = Sort.Direction.valueOf(direction);

        //TODO: finish this endpoint
        return null;
    }

}
