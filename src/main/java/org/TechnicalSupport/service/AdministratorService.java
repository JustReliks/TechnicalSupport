package org.TechnicalSupport.service;

import org.TechnicalSupport.entity.Request;
import org.TechnicalSupport.entity.User;
import org.TechnicalSupport.entity.enums.RequestStatus;
import org.TechnicalSupport.entity.enums.UserRole;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface AdministratorService {

    Long setUserRole(User user, UserRole role);

    List<Request> fetchPageOfRequests(int page, int countOnPage, Sort.Direction direction, String sortParam);

    List<Request> fetchPageOfRequestsFiltered(String name, int page, int countOnPage, Sort.Direction direction, String sortParam);

    List<Request> fetchPageOfRequestsForStatus(RequestStatus status, int page, int countOnPage, Sort.Direction direction, String sortParam);

    List<Request> fetchPageOfRequestsForStatusFiltered(RequestStatus status, String name, int page, int countOnPage, Sort.Direction direction, String sortParam);


}
