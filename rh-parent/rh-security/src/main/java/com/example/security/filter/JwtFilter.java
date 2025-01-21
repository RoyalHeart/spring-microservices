package com.example.security.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.security.service.jwt.JwtService;
import com.example.security.service.user.UserDetailsImpl;
import com.example.security.service.user.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ApplicationContext applicationContext;

    // Method to lazily fetch the UserService bean from the ApplicationContext
    // This is done to avoid Circular Dependency issues
    private UserDetailsServiceImpl getUserService() {
        return applicationContext.getBean(UserDetailsServiceImpl.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Extracting token from the request header
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Extracting the token from the Authorization header
            token = authHeader.substring(7);
            // Extracting username from the token
            userName = jwtService.extractUserName(token);
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetailsImpl userDetails = getUserService().loadUserByUsername(userName);

            if (jwtService.validateToken(request, token, userDetails)) {
                // Creating an authentication token using UserDetails
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                // Setting authentication details
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Setting the authentication token in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Proceeding with the filter chain
        filterChain.doFilter(request, response);
    }
}
