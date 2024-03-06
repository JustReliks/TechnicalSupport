package org.TechnicalSupport.service.impl;

import lombok.RequiredArgsConstructor;
import org.TechnicalSupport.entity.User;
import org.TechnicalSupport.entity.enums.UserRole;
import org.TechnicalSupport.repository.RoleRepository;
import org.TechnicalSupport.repository.UserRepository;
import org.TechnicalSupport.security.JwtUserDetails;
import org.TechnicalSupport.security.jwt.JwtUtils;
import org.TechnicalSupport.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public JwtUserDetails authenticate(String username, String password) {
        String jwt;
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        jwt = jwtUtils.generateJwtToken(authentication);
        ((JwtUserDetails) authentication.getPrincipal()).setToken(jwt);

        return (JwtUserDetails) authentication.getPrincipal();
    }

    @Override
    @Transactional
    public Long save(User user) {
        user.setRoles(Collections.singleton(roleRepository.findByRoleEnum(UserRole.USER).orElseThrow()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user).getId();

    }
}
