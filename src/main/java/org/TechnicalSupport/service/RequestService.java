package org.TechnicalSupport.service;

import org.TechnicalSupport.entity.Request;
import org.TechnicalSupport.entity.User;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface RequestService {

    Long save(Request request);

    List<Request> fetchPageOfRequests(User user, int page, int countOnPage, String sortParam, Sort.Direction direction);

}
