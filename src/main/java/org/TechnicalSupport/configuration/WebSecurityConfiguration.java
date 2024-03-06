package org.TechnicalSupport.configuration;

import lombok.RequiredArgsConstructor;
import org.TechnicalSupport.controllers.AdministratorController;
import org.TechnicalSupport.controllers.AuthenticationController;
import org.TechnicalSupport.controllers.OperatorController;
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
                        registry.requestMatchers(AuthenticationController.LOGIN_USER)
                                .permitAll()
                                .requestMatchers(AuthenticationController.REGISTER_USER)
                                .permitAll()
                                .requestMatchers(UserController.CREATE_REQUEST,
                                        UserController.FETCH_REQUESTS).hasAnyAuthority(UserRole.USER.name())

                                .requestMatchers(OperatorController.FETCH_REQUESTS,
                                        OperatorController.FETCH_REQUEST_BY_ID,
                                        OperatorController.FETCH_REQUESTS_FOR_USER,
                                        OperatorController.CHANGE_REQUEST_STATUS).hasAnyAuthority(UserRole.OPERATOR.name())

                                .requestMatchers(AdministratorController.LIST_ALL_USERS).hasAnyAuthority(UserRole.ADMIN.name())
                                .anyRequest()
                                .authenticated())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout.logoutUrl(AuthenticationController.LOGOUT_USER)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"));
        return http.build();

    }
}
