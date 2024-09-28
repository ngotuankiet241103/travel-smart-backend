package com.identity_service.identity.service.impl;

import com.identity_service.identity.entity.UserEntity;
import com.identity_service.identity.exception.CustomRuntimeException;
import com.identity_service.identity.exception.ErrorCode;
import com.identity_service.identity.repository.TokenRepository;
import com.identity_service.identity.service.TokenProviderService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenProviderServiceImpl implements TokenProviderService {
    @NonFinal
    @Value("${token.tokenSecret}")
    protected String SIGNER_KEY;
    @Value("${token.refreshExpiration}")
    private long expiredRefreshToken;
    @Value("${token.accessExpiration}")
    private long expiredAccessToken;
    @Value("${token.tokenSecret}")
    private String tokenSecret;

    private final TokenRepository tokenRepository;

    private String buildToken(UserEntity userEntity,long timeExpired) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(userEntity.getId())
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(timeExpired, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("id",userEntity.getId())
                .claim("scope",buildScope(userEntity))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header,payload);
        try{
            jwsObject.sign(new MACSigner(tokenSecret.getBytes()));
            return jwsObject.serialize();
        }
        catch (JOSEException exception){
            System.out.println(exception.getMessage());
            throw  new RuntimeException(exception.getMessage());
        }

    }
    @Override
    public SignedJWT verifyToken(String token,boolean isRefresh) throws JOSEException, ParseException {

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        if (isRefresh && !tokenRepository.existsByToken(signedJWT.getJWTClaimsSet().getJWTID())){
            throw new CustomRuntimeException(ErrorCode.TOKEN_NOT_FOUND);
        }

        Date expiryTime =  signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        System.out.println(expiryTime);
        System.out.println(expiryTime.after(new Date()));
        System.out.println(verified);
        System.out.println(signedJWT);
        if (!(verified && expiryTime.after(new Date()))){

            throw new CustomRuntimeException(ErrorCode.UNAUTHORIZED);
        }



        return signedJWT;
    }
    private String buildScope(UserEntity user) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role -> {
                System.out.println(role);
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions()))
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
            });

        return stringJoiner.toString();
    }

    @Override
    public String buildAccessToken(UserEntity userEntity) {
        return buildToken(userEntity,expiredAccessToken);
    }

    @Override
    public String buildRefreshToken(UserEntity userEntity) {
        return buildToken(userEntity,expiredRefreshToken);
    }
}
