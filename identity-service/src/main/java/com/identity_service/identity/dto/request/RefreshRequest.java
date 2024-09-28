package com.identity_service.identity.dto.request;

import lombok.Data;

@Data
public class RefreshRequest {
    private String token;
}
