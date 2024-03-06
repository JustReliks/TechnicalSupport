package org.TechnicalSupport.controllers;

import lombok.RequiredArgsConstructor;
import org.TechnicalSupport.dto.RequestDto;
import org.TechnicalSupport.dto.SimpleRequestDto;
import org.TechnicalSupport.dto.factories.DtoFactory;
import org.TechnicalSupport.entity.Request;
import org.TechnicalSupport.entity.User;
import org.TechnicalSupport.entity.enums.RequestStatus;
import org.TechnicalSupport.exception.RequestNotFoundException;
import org.TechnicalSupport.exception.UserNotFoundException;
import org.TechnicalSupport.exception.WrongRequestStatusException;
import org.TechnicalSupport.repository.RequestRepository;
import org.TechnicalSupport.repository.StatusRepository;
import org.TechnicalSupport.service.UserService;
import org.TechnicalSupport.utils.ApplicationAuditingAware;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

    public static final String CREATE_REQUEST = "/api/v1/user/request/create";
    public static final String EDIT_REQUEST = "/api/v1/user/request/edit/{id}";
    public static final String FETCH_REQUESTS = "/api/v1/user/request/{page}";

    private static final int REQUESTS_PER_PAGE = 5;
    private static final String FETCH_PARAM = "createdAt";


    private final DtoFactory<Request, RequestDto> requestDtoFactory;

    private final UserService userService;
    private final StatusRepository statusRepository;
    private final ApplicationAuditingAware auditingAware;
    private final RequestRepository requestRepository;

    @PostMapping(CREATE_REQUEST)
    public Long openRequest(@RequestBody SimpleRequestDto dto, @RequestParam(defaultValue = "draft") String status) {
        RequestStatus requestStatus;
        try {
            requestStatus = RequestStatus.valueOf(status.toUpperCase());
            if (requestStatus == RequestStatus.ACCEPTED || requestStatus == RequestStatus.REJECTED) {
                throw new IllegalArgumentException();
            }

        } catch (IllegalArgumentException e) {
            throw new WrongRequestStatusException("Request status %s is incorrect!".formatted(status));
        }

        Request request = createRequest(dto, requestStatus);
        return userService.save(request);
    }


    @PatchMapping(EDIT_REQUEST)
    @Transactional
    public Long editRequest(@RequestBody SimpleRequestDto simpleRequestDto,
                            @PathVariable("id") Long id) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new RequestNotFoundException("Request with id %s not found".formatted(id)));

        if (request.getStatus().getStatusEnum() != RequestStatus.DRAFT) {
            throw new WrongRequestStatusException("You can't edit request which status is not \"draft\"");
        }

        request.setName(simpleRequestDto.getName());
        request.setMessage(simpleRequestDto.getMessage());
        request.setPhoneNumber(simpleRequestDto.getPhoneNumber());
        return userService.save(request);
    }

    @GetMapping(FETCH_REQUESTS)
    public List<RequestDto> fetchRequests(@PathVariable int page, @RequestParam(defaultValue = "ASC") String direction) {
        return userService.fetchPageOfRequests(getCurrentUser()
                        , page - 1, REQUESTS_PER_PAGE,
                        Sort.Direction.valueOf(direction.toUpperCase()), FETCH_PARAM)
                .stream()
                .map(requestDtoFactory::toDto)
                .collect(Collectors.toList());
    }

    private Request createRequest(SimpleRequestDto dto, RequestStatus status) {
        return Request.builder()
                .name(dto.getName())
                .author(getCurrentUser())
                .message(dto.getMessage())
                .phoneNumber(dto.getPhoneNumber())
                .status(statusRepository.findByStatusEnum(status).orElseThrow())
                .build();
    }

    private User getCurrentUser() {
        return auditingAware.getCurrentAuditor().orElseThrow(() -> new UserNotFoundException("Cant find current user"));
    }

}
