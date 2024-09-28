package com.identity_service.identity.service.impl;

import com.identity_service.identity.dto.response.TokenResponse;
import com.identity_service.identity.entity.TokenEntity;
import com.identity_service.identity.entity.UserEntity;
import com.identity_service.identity.repository.TokenRepository;
import com.identity_service.identity.service.TokenProviderService;
import com.identity_service.identity.service.TokenService;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;
    private final TokenProviderService tokenProviderService;

    @Override
    public TokenResponse buildToken(UserEntity userEntity) {
        String accessToken = tokenProviderService.buildAccessToken(userEntity);
        String refreshToken = tokenProviderService.buildRefreshToken(userEntity);
        saveToken(refreshToken, userEntity);
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Async
    private void saveToken(String token,UserEntity userEntity)  {
        try{

            SignedJWT signedJWT = SignedJWT.parse(token);
            System.out.println(signedJWT);


            TokenEntity tokenEntity  = TokenEntity.builder()
                    .token(signedJWT.getJWTClaimsSet().getJWTID())
                    .user(userEntity)
                    .build();
            tokenRepository.save(tokenEntity);
        }
        catch (ParseException e) {
            System.out.println(e.getMessage());
        }

    }
}
