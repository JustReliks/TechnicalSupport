package org.TechnicalSupport.service.impl;

import lombok.RequiredArgsConstructor;
import org.TechnicalSupport.entity.Role;
import org.TechnicalSupport.entity.User;
import org.TechnicalSupport.entity.enums.UserRole;
import org.TechnicalSupport.repository.RoleRepository;
import org.TechnicalSupport.repository.UserRepository;
import org.TechnicalSupport.service.AdministratorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdministratorServiceImpl implements AdministratorService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Long setUserRole(User user, UserRole role) {
        Role roleEntity = roleRepository.findByRoleEnum(role).orElseThrow();
        user.getRoles().add(roleEntity);

        return userRepository.save(user).getId();
    }
}
