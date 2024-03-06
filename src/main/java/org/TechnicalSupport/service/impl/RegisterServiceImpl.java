package org.TechnicalSupport.service.impl;

import lombok.RequiredArgsConstructor;
import org.TechnicalSupport.entity.User;
import org.TechnicalSupport.service.RegisterService;
import org.TechnicalSupport.service.AuthenticationService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final AuthenticationService authenticationService;

    @Override
    public Long register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return authenticationService.save(user);
    }
}
