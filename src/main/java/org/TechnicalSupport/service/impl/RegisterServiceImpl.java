package org.TechnicalSupport.service.impl;

import lombok.RequiredArgsConstructor;
import org.TechnicalSupport.entity.User;
import org.TechnicalSupport.service.RegisterService;
import org.TechnicalSupport.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UserService userService;

    @Override
    public Long register(String username, String password) {
       // password = new String(Base64.getDecoder().decode(password));
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return userService.save(user);
    }
}
