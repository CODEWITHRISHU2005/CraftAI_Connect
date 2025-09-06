package com.CODEWITHRISHU.CraftAI_Connect.controller;

import com.CODEWITHRISHU.CraftAI_Connect.dto.Request.LoginRequest;
import com.CODEWITHRISHU.CraftAI_Connect.dto.Request.RefreshTokenRequest;
import com.CODEWITHRISHU.CraftAI_Connect.dto.Request.RegisterRequest;
import com.CODEWITHRISHU.CraftAI_Connect.dto.Response.JwtResponse;
import com.CODEWITHRISHU.CraftAI_Connect.entity.RefreshToken;
import com.CODEWITHRISHU.CraftAI_Connect.service.JwtService;
import com.CODEWITHRISHU.CraftAI_Connect.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signIn")
    public JwtResponse authenticateAndGetToken(@RequestBody LoginRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getUsername());
        return JwtResponse.builder()
                .accessToken(jwtService.generateToken(authRequest.getUsername()))
                .refreshToken(refreshToken.getToken()).build();
    }

    @PostMapping("/refreshToken")
    public JwtResponse getRefreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.generateToken(userInfo.getName());
                    return JwtResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequest.getToken())
                            .build();
                }).orElseThrow(() -> new RuntimeException(
                        "Refresh token is not in database!"));
    }

    @PostMapping("/signUp")
    public JwtResponse registerAndGetAccessAndRefreshToken(@RequestBody RegisterRequest request) {
        jwtService.addUser(request);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.getName());

        return JwtResponse.builder()
                .accessToken(jwtService.generateToken(request.getName()))
                .refreshToken(refreshToken.getToken()).build();
    }
}