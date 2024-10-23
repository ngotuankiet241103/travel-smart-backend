package com.identity_service.identity.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotEmpty(message = "EMAIL_INVALID")
    private String email;
    @NotEmpty(message = "PASSWORD_INVALID")
    private String password;
    @NotEmpty(message = "FIRST_NAME_INVALID")
    private String firstName;
    @NotEmpty(message = "LAST_NAME_INVALID")
    private String lastName;
}
