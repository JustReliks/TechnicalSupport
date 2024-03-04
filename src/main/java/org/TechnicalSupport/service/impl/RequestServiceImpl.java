package org.TechnicalSupport.service.impl;

import lombok.RequiredArgsConstructor;
import org.TechnicalSupport.entity.Request;
import org.TechnicalSupport.repository.RequestRepository;
import org.TechnicalSupport.service.RequestService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository repository;

    @Override
    public Long save(Request request) {
        return repository.save(request).getId();
    }
}
