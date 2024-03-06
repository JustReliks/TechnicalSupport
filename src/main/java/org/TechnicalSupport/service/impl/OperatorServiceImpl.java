package org.TechnicalSupport.service.impl;

import lombok.RequiredArgsConstructor;
import org.TechnicalSupport.entity.Request;
import org.TechnicalSupport.entity.User;
import org.TechnicalSupport.entity.enums.RequestStatus;
import org.TechnicalSupport.repository.RequestRepository;
import org.TechnicalSupport.repository.StatusRepository;
import org.TechnicalSupport.service.OperatorService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.TechnicalSupport.utils.PageableUtils.getPageable;

@Service
@RequiredArgsConstructor
public class OperatorServiceImpl implements OperatorService {

    private final RequestRepository requestRepository;
    private final StatusRepository statusRepository;

    @Override
    public List<Request> fetchPageOfRequests(int page, int countOnPage, Sort.Direction direction, String sortParam) {
        return requestRepository.findAllByStatus(statusRepository.findByStatusEnum(RequestStatus.SENT).orElseThrow(), getPageable(page, countOnPage, direction, sortParam)).getContent();
    }

    @Override
    public List<Request> fetchPageOfRequestsFiltered(String name, int page, int countOnPage, Sort.Direction direction, String sortParam) {
        return requestRepository.findAllByNameContainingAndStatus(name, statusRepository.findByStatusEnum(RequestStatus.SENT).orElseThrow(), getPageable(page, countOnPage, direction, sortParam)).getContent();
    }


    @Override
    public List<Request> fetchPageOfRequestsForUser(User user, int page, int countOnPage, Sort.Direction direction, String sortParam) {
        return requestRepository.findAllByAuthorAndStatus(user, statusRepository.findByStatusEnum(RequestStatus.SENT).orElseThrow(), getPageable(page, countOnPage, direction, sortParam)).getContent();
    }

    @Override
    public List<Request> fetchPageOfRequestsForUserFiltered(User user, String name, int page, int countOnPage, Sort.Direction direction, String sortParam) {
        return requestRepository.findAllByAuthorAndNameContainingAndStatus(user, name, statusRepository.findByStatusEnum(RequestStatus.SENT).orElseThrow(), getPageable(page, countOnPage, direction, sortParam)).getContent();
    }

    @Override
    @Transactional
    public Long changeRequestStatus(Request request, RequestStatus status) {
        request.setStatus(statusRepository.findByStatusEnum(status).orElseThrow());
        return requestRepository.save(request).getId();
    }
}

