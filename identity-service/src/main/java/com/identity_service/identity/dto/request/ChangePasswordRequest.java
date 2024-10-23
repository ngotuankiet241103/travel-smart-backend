package com.identity_service.identity.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @NotEmpty(message = "PASSWORD_INVALID")
    private String oldPassword;
    @NotEmpty(message = "PASSWORD_INVALID")
    private String newPassword;
}
