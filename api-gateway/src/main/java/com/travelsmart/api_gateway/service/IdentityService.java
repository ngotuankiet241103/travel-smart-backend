package com.travelsmart.api_gateway.service;

import com.travelsmart.api_gateway.dto.response.ApiResponse;
import com.travelsmart.api_gateway.dto.response.IntrospectResponse;
import reactor.core.publisher.Mono;

public interface IdentityService {
    Mono<ApiResponse<IntrospectResponse>> introspect(String token);
}
