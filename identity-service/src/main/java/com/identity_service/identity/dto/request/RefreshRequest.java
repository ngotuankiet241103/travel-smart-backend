package com.identity_service.identity.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RefreshRequest {
    @NotEmpty(message = "TOKEN_INVALID")
    private String token;
}
