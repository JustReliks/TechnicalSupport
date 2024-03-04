package org.TechnicalSupport.controllers;

import lombok.RequiredArgsConstructor;
import org.TechnicalSupport.dto.RequestDto;
import org.TechnicalSupport.entity.Request;
import org.TechnicalSupport.entity.enums.RequestStatus;
import org.TechnicalSupport.exception.WrongRequestStatusException;
import org.TechnicalSupport.repository.RoleRepository;
import org.TechnicalSupport.repository.StatusRepository;
import org.TechnicalSupport.repository.UserRepository;
import org.TechnicalSupport.service.RequestService;
import org.TechnicalSupport.utils.ApplicationAuditingAware;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RequestController {

    public static final String CREATE_REQUEST = "/api/v1/request/create";


    private final UserRepository userRepository;
    private final RequestService requestService;
    private final StatusRepository statusRepository;
    private final ApplicationAuditingAware auditingAware;
    private final RoleRepository roleRepository;


    @PostMapping(CREATE_REQUEST)
    public Long openRequest(@RequestBody RequestDto dto, @RequestParam(defaultValue = "draft") String status) {
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
        return requestService.save(request);
    }

    private Request createRequest(RequestDto dto, RequestStatus status) {
        return Request.builder()
                .name(dto.getName())
                .createdBy(userRepository.findById(auditingAware
                                .getCurrentAuditor()
                                .orElseThrow())
                        .orElseThrow())
                .message(dto.getText())
                .phoneNumber(dto.getPhoneNumber())
                .status(statusRepository.findByStatusEnum(status).orElseThrow())
                .build();
    }

}
