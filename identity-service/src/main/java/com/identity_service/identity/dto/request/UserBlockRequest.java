package com.identity_service.identity.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserBlockRequest {
    @NotNull(message = "BLOCK_INVALID")
    private boolean isBlock;
}
