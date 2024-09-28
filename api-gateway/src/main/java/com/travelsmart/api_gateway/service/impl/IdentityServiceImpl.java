package com.travelsmart.api_gateway.service.impl;

import com.travelsmart.api_gateway.dto.request.IntrospectRequest;
import com.travelsmart.api_gateway.dto.response.ApiResponse;
import com.travelsmart.api_gateway.dto.response.IntrospectResponse;
import com.travelsmart.api_gateway.repository.IdentityClient;
import com.travelsmart.api_gateway.service.IdentityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor

public class IdentityServiceImpl implements IdentityService {
    private final IdentityClient identityClient;

    public Mono<ApiResponse<IntrospectResponse>> introspect(String token){
        return identityClient.introspect(IntrospectRequest.builder()
                .token(token)
                .build());
    }
}
