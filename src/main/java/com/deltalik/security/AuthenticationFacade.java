package com.deltalik.security;

import com.deltalik.entity.User;
import com.deltalik.exception.ExceptionFactory;
import com.deltalik.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationFacade {

    private final UserService userService;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw ExceptionFactory.authenticatedUserNotFound();
        }

        String email = authentication.getName();
        return userService.getUserEntityByEmail(email);
    }

    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw ExceptionFactory.authenticatedUserNotFound();
        }

        return authentication.getName();
    }

    public boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw ExceptionFactory.authenticatedUserNotFound();
        }

        return authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_" + role));
    }
}
