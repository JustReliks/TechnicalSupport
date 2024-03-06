package org.TechnicalSupport.service;

import org.TechnicalSupport.entity.Request;
import org.TechnicalSupport.entity.User;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface UserService {

    Long save(Request request);

    List<Request> fetchPageOfRequests(User user, int page, int countOnPage,Sort.Direction direction, String... sortParams);

}
