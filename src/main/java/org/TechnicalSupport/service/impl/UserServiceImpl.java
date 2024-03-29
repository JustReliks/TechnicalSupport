package org.TechnicalSupport.service.impl;

import lombok.RequiredArgsConstructor;
import org.TechnicalSupport.entity.Request;
import org.TechnicalSupport.entity.User;
import org.TechnicalSupport.repository.RequestRepository;
import org.TechnicalSupport.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RequestRepository requestRepository;

    @Override
    public Long save(Request request) {
        return requestRepository.save(request).getId();
    }

    @Override
    public List<Request> fetchPageOfRequests(User user, int page, int countOnPage, Sort.Direction direction, String... sortParams) {
        Sort sort = Sort.by(direction, sortParams);
        return requestRepository.findAllByAuthor(user, PageRequest.of(page, countOnPage, sort))
                .getContent();
    }
}
