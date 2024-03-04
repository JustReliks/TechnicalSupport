package org.TechnicalSupport.service;

import org.TechnicalSupport.dto.UserLoginRequestDto;
import org.TechnicalSupport.security.JwtUserDetails;

public interface LoginService {

    JwtUserDetails login(UserLoginRequestDto loginRequestDto);

    JwtUserDetails getCurrentUser();

}
