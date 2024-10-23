package com.identity_service.identity.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserForgotPassRequest {
    @NotEmpty(message = "EMAIL_INVALID")
    private String email;
    private String urlRedirect;
}
