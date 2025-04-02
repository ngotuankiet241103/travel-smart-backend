package com.identity_service.identity.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.identity_service.identity.entity.UserEntity;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface TokenRedisService {
    void setToken(String token, UserEntity userEntity) throws JsonProcessingException;
    String getToken() throws JsonProcessingException;
    String getToken(String token,boolean isBlackList) throws JsonProcessingException, ParseException, JOSEException;
    void expiredToken(String token) throws ParseException, JOSEException, JsonProcessingException;
}
