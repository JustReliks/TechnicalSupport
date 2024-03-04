package org.TechnicalSupport.configuration;

import lombok.RequiredArgsConstructor;
import org.TechnicalSupport.controllers.RequestController;
import org.TechnicalSupport.controllers.UserController;
import org.TechnicalSupport.entity.enums.UserRole;
import org.TechnicalSupport.security.jwt.JwtAuthenticationEntryPoint;
import org.TechnicalSupport.security.jwt.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final JwtTokenFilter jwtAuthFilter;
    private final JwtAuthenticationEntryPoint point;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(registry ->
                        registry.requestMatchers(UserController.LOGIN_USER)
                                .permitAll()
                                .requestMatchers(UserController.REGISTER_USER)
                                .permitAll()
                                .requestMatchers(UserController.LIST_ALL_USERS, RequestController.CREATE_REQUEST).hasAnyAuthority(UserRole.USER.name())
                                .anyRequest()
                                .authenticated())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
}
