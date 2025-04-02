package com.identity_service.identity.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.identity_service.identity.entity.UserEntity;
import com.identity_service.identity.service.TokenProviderService;
import com.identity_service.identity.service.TokenRedisService;
import com.identity_service.identity.service.TokenService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenRedisServiceImpl implements TokenRedisService {
    private final BaseRedisImplService<String> redisService;
    private final String session_key = "session:";
    private final String expired_key = "blacklist:";
    private final TokenProviderService tokenProviderService;
    @Override
    public void setToken(String token, UserEntity userEntity) throws JsonProcessingException {

        String key = createKey(session_key,userEntity.getId());
        redisService.save(key,token);

    }

    @Override
    public String getToken() throws JsonProcessingException {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        String key = createKey(session_key,userId);
        return redisService.get(key);
    }

    @Override
    public String getToken(String token,boolean isBlackList) throws JsonProcessingException, ParseException, JOSEException {
        SignedJWT claim = tokenProviderService.parseToken(token);
        String userId = claim.getJWTClaimsSet().getClaims().get("id").toString();
        String key = isBlackList ? createKey(expired_key,userId,":",token) : createKey(session_key,userId);
        return redisService.get(key);
    }

    @Override
    public void expiredToken(String token) throws ParseException, JOSEException, JsonProcessingException {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        String key = createKey(expired_key,userId,":",token);
        SignedJWT claim = tokenProviderService.parseToken(token);
        long time = claim.getJWTClaimsSet().getExpirationTime().getTime();
        redisService.save(key,token,  time, TimeUnit.MILLISECONDS);
    }
    private String createKey(String... args){
        StringBuilder response = new StringBuilder();
        for (String arg : args) {
            response.append(arg);
        }
        return response.toString();
    }

}
