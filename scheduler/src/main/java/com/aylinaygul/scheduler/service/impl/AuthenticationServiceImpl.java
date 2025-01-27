package com.aylinaygul.scheduler.service.impl;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.aylinaygul.scheduler.dto.DtoUser;
// import com.aylinaygul.scheduler.exception.BaseException;
// import com.aylinaygul.scheduler.exception.ErrorMessage;
// import com.aylinaygul.scheduler.exception.MessageType;
import com.aylinaygul.scheduler.jwt.AuthRequest;
import com.aylinaygul.scheduler.jwt.AuthResponse;
import com.aylinaygul.scheduler.jwt.JWTService;
import com.aylinaygul.scheduler.jwt.RefreshTokenRequest;
import com.aylinaygul.scheduler.model.RefreshToken;
import com.aylinaygul.scheduler.model.User;
import com.aylinaygul.scheduler.repository.IRefreshTokenRepository;
import com.aylinaygul.scheduler.repository.IUserRepository;
import com.aylinaygul.scheduler.service.IAuthenticationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final IUserRepository userRepository;

    private final AuthenticationProvider authenticationProvider;

    private final JWTService jwtService;

    private final IRefreshTokenRepository refreshTokenRepository;

    private User createUser(AuthRequest request) {
        User user = new User();
        user.setCreatedAt(new Date());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return user;
    }

    private RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setCreatedAt(new Date());
        refreshToken.setExpireDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);

        return refreshToken;
    }

    private boolean isRefreshTokenValid(Date expireDate) {
        return new Date().before(expireDate);
    }

    @Override
    public DtoUser register(AuthRequest request) {
        User savedUser = userRepository.save(createUser(request));

        DtoUser dtoUser = new DtoUser();
        BeanUtils.copyProperties(savedUser, dtoUser);
        return dtoUser;
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword());
        try {
            authenticationProvider.authenticate(authenticationToken);
            Optional<User> optional = userRepository.findByUsername(authRequest.getUsername());

            String accessToken = jwtService.generateToken(optional.get());
            RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(optional.get()));

            return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        Optional<RefreshToken> optRefreshToken = refreshTokenRepository.findByRefreshToken(request.getRefreshToken());
        if (optRefreshToken.isEmpty()) {
        }

        if (!isRefreshTokenValid(optRefreshToken.get().getExpireDate())) {
        }

        String accessToken = jwtService.generateToken(optRefreshToken.get().getUser());
        RefreshToken savedRefreshToken = refreshTokenRepository
                .save(createRefreshToken(optRefreshToken.get().getUser()));

        return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());
    }

}