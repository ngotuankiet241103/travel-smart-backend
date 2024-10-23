package com.identity_service.identity.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest {
    @NotEmpty(message = "EMAIL_INVALID")
    private String email;
    @NotEmpty(message = "PASSWORD_INVALID")
    private String password;
}
