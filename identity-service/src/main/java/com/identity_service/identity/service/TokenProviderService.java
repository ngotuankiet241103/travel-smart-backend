package com.identity_service.identity.service;

import com.identity_service.identity.entity.UserEntity;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;

public interface TokenProviderService {

    String buildAccessToken(UserEntity userEntity);
    String buildRefreshToken(UserEntity userEntity);
    SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException;
}
