package org.TechnicalSupport.service;

import org.TechnicalSupport.entity.Request;
import org.TechnicalSupport.entity.User;
import org.TechnicalSupport.entity.enums.RequestStatus;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface OperatorService {

    List<Request> fetchPageOfRequests(int page, int countOnPage, Sort.Direction direction, String sortParam);

    List<Request> fetchPageOfRequestsFiltered(String name, int page, int countOnPage, Sort.Direction direction, String sortParam);

    List<Request> fetchPageOfRequestsForUser(User user, int page, int countOnPage, Sort.Direction direction, String sortParam);

    List<Request> fetchPageOfRequestsForUserFiltered(User user, String name, int page, int countOnPage, Sort.Direction direction, String sortParam);

    Long changeRequestStatus(Request request, RequestStatus status);

}
