package com.identity_service.identity.dto.response;

import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String email;
    private boolean isBlock;
    private boolean isEnable;
}
