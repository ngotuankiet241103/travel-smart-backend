package com.identity_service.identity.dto.request;

import lombok.Data;

@Data
public class UserForgotPassRequest {
    private String email;
    private String urlRedirect;
}
