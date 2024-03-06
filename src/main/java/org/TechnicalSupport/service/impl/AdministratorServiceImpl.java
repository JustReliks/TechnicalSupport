package org.TechnicalSupport.service.impl;

import lombok.RequiredArgsConstructor;
import org.TechnicalSupport.entity.Request;
import org.TechnicalSupport.entity.Role;
import org.TechnicalSupport.entity.User;
import org.TechnicalSupport.entity.enums.RequestStatus;
import org.TechnicalSupport.entity.enums.UserRole;
import org.TechnicalSupport.repository.RequestRepository;
import org.TechnicalSupport.repository.RoleRepository;
import org.TechnicalSupport.repository.StatusRepository;
import org.TechnicalSupport.repository.UserRepository;
import org.TechnicalSupport.service.AdministratorService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.TechnicalSupport.utils.PageableUtils.getPageable;

@Service
@RequiredArgsConstructor
public class AdministratorServiceImpl implements AdministratorService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final StatusRepository statusRepository;

    @Override
    @Transactional
    public Long setUserRole(User user, UserRole role) {
        Role roleEntity = roleRepository.findByRoleEnum(role).orElseThrow();
        user.getRoles().add(roleEntity);

        return userRepository.save(user).getId();
    }

    @Override
    public List<Request> fetchPageOfRequests(int page, int countOnPage, Sort.Direction direction, String sortParam) {
        return requestRepository.findAll(getPageable(page, countOnPage, direction, sortParam)).getContent();
    }

    @Override
    public List<Request> fetchPageOfRequestsFiltered(String name, int page, int countOnPage, Sort.Direction direction, String sortParam) {
        return requestRepository.findAllByNameContaining(name, getPageable(page, countOnPage, direction, sortParam)).getContent();
    }

    @Override
    public List<Request> fetchPageOfRequestsForStatus(RequestStatus status, int page, int countOnPage, Sort.Direction direction, String sortParam) {
        return requestRepository.findAllByStatus(statusRepository.findByStatusEnum(status).orElseThrow(), getPageable(page, countOnPage, direction, sortParam)).getContent();
    }

    @Override
    public List<Request> fetchPageOfRequestsForStatusFiltered(RequestStatus status, String name, int page, int countOnPage, Sort.Direction direction, String sortParam) {
        return requestRepository.findAllByNameContainingAndStatus(name, statusRepository.findByStatusEnum(status).orElseThrow(), getPageable(page, countOnPage, direction, sortParam)).getContent();
    }
}
