package com.aylinaygul.scheduler.controller;

import com.aylinaygul.scheduler.dto.DtoUser;
import com.aylinaygul.scheduler.jwt.AuthRequest;
import com.aylinaygul.scheduler.jwt.AuthResponse;
import com.aylinaygul.scheduler.jwt.RefreshTokenRequest;

public interface IRestAuthenticationController {

    public DtoUser register(AuthRequest request);

    public AuthResponse authenticate(AuthRequest request);

    public AuthResponse refreshToken(RefreshTokenRequest request);

}
