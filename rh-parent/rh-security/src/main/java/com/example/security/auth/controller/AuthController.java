package com.example.security.auth.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.core.api.controller.AbstractRestController;
import com.example.core.api.res.BaseResponse;
import com.example.security.auth.req.LoginRequest;
import com.example.security.auth.req.RefreshTokenRequest;
import com.example.security.auth.res.LoginResponse;
import com.example.security.auth.res.RandomStuff;
import com.example.security.jwt.JwtService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController extends AbstractRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public BaseResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        long start = System.currentTimeMillis();
        try {
            log.info(">>>Login: " + loginRequest.toString());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            List<String> roles = new ArrayList<>();
            roles.add("user");
            String accessToken = jwtService.generateAccessToken(loginRequest.getUsername(),roles);
            String refreshToken = jwtService.generateRefreshToken(loginRequest.getUsername());
            LoginResponse loginResponse = new LoginResponse(accessToken, refreshToken);
            return responseHandler.handleSuccessRequest(loginResponse, start);
        } catch (AuthenticationException e) {
            return responseHandler.handleErrorRequest(e, null, start);
        } catch (Exception e) {
            return responseHandler.handleErrorRequest(e, null, start);
        }
    }

    @PostMapping("/refresh-token")
    public BaseResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        long start = System.currentTimeMillis();
        String refreshToken = refreshTokenRequest.getRefreshToken();
        try {
            log.info(">>> Refresh Token: " + refreshTokenRequest.toString());
            List<String> roles = new ArrayList<>();
            roles.add("user");
            String username = jwtService.extractUserName(refreshToken);
            String accessToken = jwtService.generateAccessToken(username, roles);
            LoginResponse loginResponse = new LoginResponse(accessToken, refreshToken);
            return responseHandler.handleSuccessRequest(loginResponse, start);
        } catch (AuthenticationException e) {
            return responseHandler.handleErrorRequest(e, null, start);
        } catch (Exception e) {
            return responseHandler.handleErrorRequest(e, null, start);
        }
    }

    @GetMapping("/random")
    public BaseResponse getRandomStuff() {
        long start = System.currentTimeMillis();
        log.info(">>> Random mess");
        RandomStuff randomStuff = new RandomStuff("This is a random message");
        return responseHandler.handleSuccessRequest(randomStuff, start);
    }

}
