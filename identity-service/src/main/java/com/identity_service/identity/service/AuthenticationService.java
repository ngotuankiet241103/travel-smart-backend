package com.identity_service.identity.service;

import com.identity_service.identity.dto.request.*;
import com.identity_service.identity.dto.response.IntrospectResponse;
import com.identity_service.identity.dto.response.TokenResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    TokenResponse login(AuthenticationRequest authenticationRequest);

    TokenResponse register(RegisterRequest registerRequest);

    IntrospectResponse introspect(IntrospectRequest build);

    void verifyEmail();

    void verifyToken(String token);

    void forgotPassword(UserForgotPassRequest forgotPassRequest);

    String resetPassword(ResetPasswordRequest resetPasswordRequest);

    void logout(RefreshRequest refreshRequest) throws ParseException, JOSEException;

    TokenResponse refreshToken(RefreshRequest refreshRequest) throws ParseException;
}
