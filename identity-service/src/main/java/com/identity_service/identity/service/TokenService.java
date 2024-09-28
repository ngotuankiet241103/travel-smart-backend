package com.identity_service.identity.service;

import com.identity_service.identity.dto.response.TokenResponse;
import com.identity_service.identity.entity.UserEntity;

public interface TokenService {
    TokenResponse buildToken(UserEntity user);
}
