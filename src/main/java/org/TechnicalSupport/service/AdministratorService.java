package org.TechnicalSupport.service;

import org.TechnicalSupport.entity.User;
import org.TechnicalSupport.entity.enums.UserRole;

public interface AdministratorService {

    Long setUserRole(User user, UserRole role);

}
