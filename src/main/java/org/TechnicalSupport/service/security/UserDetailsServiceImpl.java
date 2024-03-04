package org.TechnicalSupport.service.security;

import lombok.RequiredArgsConstructor;
import org.TechnicalSupport.entity.User;
import org.TechnicalSupport.repository.UserRepository;
import org.TechnicalSupport.security.JwtUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with name: " + username));


        return JwtUserDetails.build(user);
    }

    public JwtUserDetails getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof JwtUserDetails) {
            return (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return null;
    }
}
