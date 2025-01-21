package com.example.security.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.security.service.user.UserDetailsImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProfileUtil {
    @Autowired
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Autowired
    public static UserDetailsImpl getUserPrincipal() {
        if (getAuthentication() != null
                && getAuthentication().getPrincipal() instanceof UserDetailsImpl userDetailsImpl) {
            userDetailsImpl = (UserDetailsImpl) getAuthentication().getPrincipal();
            return userDetailsImpl;
        }
        return null;
    }

    private ProfileUtil() {
    }

    public static String getUsername() {
        return getUserPrincipal().getUsername();
    }

    public static List<String> getRoles() {
        return getUserPrincipal().getAuthorities().stream().map(auth -> auth.getAuthority())
                .toList();
    }
}
