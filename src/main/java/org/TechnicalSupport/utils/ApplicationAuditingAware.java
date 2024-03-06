package org.TechnicalSupport.utils;

import lombok.RequiredArgsConstructor;
import org.TechnicalSupport.entity.User;
import org.TechnicalSupport.repository.UserRepository;
import org.TechnicalSupport.security.JwtUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ApplicationAuditingAware implements AuditorAware<User> {

    private final UserRepository userRepository;

    @Override
    public Optional<User> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        JwtUserDetails userPrincipal = (JwtUserDetails) authentication.getPrincipal();
        return userRepository.findById(userPrincipal.getId());
    }
}
