package com.aylinaygul.scheduler.controller.impl;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aylinaygul.scheduler.controller.IRestAuthenticationController;
import com.aylinaygul.scheduler.dto.DtoUser;
import com.aylinaygul.scheduler.jwt.AuthRequest;
import com.aylinaygul.scheduler.jwt.AuthResponse;
import com.aylinaygul.scheduler.jwt.RefreshTokenRequest;
import com.aylinaygul.scheduler.service.IAuthenticationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RestAuthenticationControllerImpl implements IRestAuthenticationController {

    private final IAuthenticationService authenticationService;

    @PostMapping("/register")
    @Override
    public DtoUser register(@Valid @RequestBody AuthRequest request) {
        return authenticationService.register(request);
    }

    @PostMapping("/authenticate")
    @Override
    public AuthResponse authenticate(@Valid @RequestBody AuthRequest request) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/refreshToken")
    @Override
    public AuthResponse refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return authenticationService.refreshToken(request);
    }

}