package org.TechnicalSupport.service;

import org.TechnicalSupport.entity.User;
import org.TechnicalSupport.security.JwtUserDetails;

public interface UserService {

   JwtUserDetails authenticate(String username, String password);
   Long save(User user);

}
