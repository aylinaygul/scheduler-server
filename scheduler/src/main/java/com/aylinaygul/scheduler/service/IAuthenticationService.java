package com.aylinaygul.scheduler.service;

import com.aylinaygul.scheduler.dto.DtoUser;
import com.aylinaygul.scheduler.jwt.AuthRequest;
import com.aylinaygul.scheduler.jwt.AuthResponse;
import com.aylinaygul.scheduler.jwt.RefreshTokenRequest;

public interface IAuthenticationService {

    DtoUser register(AuthRequest request);

    AuthResponse authenticate(AuthRequest authRequest);

    AuthResponse refreshToken(RefreshTokenRequest request);
}