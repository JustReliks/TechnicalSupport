package org.TechnicalSupport.controllers;

import lombok.RequiredArgsConstructor;
import org.TechnicalSupport.dto.RequestDto;
import org.TechnicalSupport.dto.factories.DtoFactory;
import org.TechnicalSupport.entity.Request;
import org.TechnicalSupport.entity.User;
import org.TechnicalSupport.entity.enums.RequestStatus;
import org.TechnicalSupport.exception.RequestNotFoundException;
import org.TechnicalSupport.exception.UserNotFoundException;
import org.TechnicalSupport.exception.WrongRequestStatusException;
import org.TechnicalSupport.repository.RequestRepository;
import org.TechnicalSupport.repository.UserRepository;
import org.TechnicalSupport.service.OperatorService;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OperatorController {

    public static final String FETCH_REQUESTS = "/api/v1/operator/requests/{page}";
    public static final String CHANGE_REQUEST_STATUS = "/api/v1/operator/request/{id}";
    public static final String FETCH_REQUEST_BY_ID = "/api/v1/operator/request/{id}";
    public static final String FETCH_REQUESTS_FOR_USER = "/api/v1/operator/requests/{user}/{page}";

    private static final int REQUESTS_PER_PAGE = 5;

    private static final String FETCH_PARAM = "createdAt";

    private final OperatorService operatorService;
    private final DtoFactory<Request, RequestDto> requestDtoFactory;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    @GetMapping(FETCH_REQUESTS)
    public List<RequestDto> fetchRequests(@PathVariable int page,
                                          @RequestParam(defaultValue = "ASC") String direction,
                                          @RequestParam(required = false, name = "name") String nameFilter) {
        Sort.Direction dir = Sort.Direction.valueOf(direction);

        if (StringUtils.hasText(nameFilter)) {
            return operatorService.fetchPageOfRequestsFiltered(nameFilter, page, REQUESTS_PER_PAGE, dir, FETCH_PARAM)
                    .stream().map(requestDtoFactory::toDto).collect(Collectors.toList());
        }
        return operatorService.fetchPageOfRequests(page, REQUESTS_PER_PAGE, dir, FETCH_PARAM)
                .stream().map(requestDtoFactory::toDto).collect(Collectors.toList());
    }

    @GetMapping(FETCH_REQUESTS_FOR_USER)
    public List<RequestDto> fetchRequestsForUser(@PathVariable int page,
                                                 @PathVariable String user,
                                                 @RequestParam(defaultValue = "ASC") String direction,
                                                 @RequestParam(required = false, name = "name") String nameFilter) {
        Sort.Direction dir = Sort.Direction.valueOf(direction);
        User userEntity = userRepository.findUserByUsername(user)
                .orElseThrow(() -> new UserNotFoundException("User with name %s not found".formatted(user)));

        if (StringUtils.hasText(nameFilter)) {
            return operatorService.fetchPageOfRequestsForUserFiltered(userEntity, nameFilter, page, REQUESTS_PER_PAGE, dir, FETCH_PARAM)
                    .stream().map(requestDtoFactory::toDto).collect(Collectors.toList());
        }
        return operatorService.fetchPageOfRequestsForUser(userEntity, page, REQUESTS_PER_PAGE, dir, FETCH_PARAM)
                .stream().map(requestDtoFactory::toDto).collect(Collectors.toList());
    }


    @GetMapping(FETCH_REQUEST_BY_ID)
    public RequestDto getRequest(@PathVariable Long id) {
        Request request = requestRepository.findById(id).orElseThrow(() -> new RequestNotFoundException("Can't find request with id %s".formatted(id)));
        return requestDtoFactory.toDto(request);
    }

    @PostMapping(CHANGE_REQUEST_STATUS)
    public Long changeStatus(@PathVariable Long id, String status) {
        RequestStatus requestStatus;
        try {
            requestStatus = RequestStatus.valueOf(status);
            if (requestStatus != RequestStatus.ACCEPTED && requestStatus != RequestStatus.REJECTED) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException exception) {
            throw new WrongRequestStatusException("Wrong request status: %s".formatted(status));
        }
        Request request = requestRepository.findById(id).orElseThrow(() -> new RequestNotFoundException("Request with id %s not found!".formatted(id)));

        return operatorService.changeRequestStatus(request, requestStatus);
    }

}
