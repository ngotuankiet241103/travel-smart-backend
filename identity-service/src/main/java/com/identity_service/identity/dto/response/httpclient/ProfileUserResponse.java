package com.identity_service.identity.dto.response.httpclient;

import lombok.Data;

@Data
public class ProfileUserResponse {
    private String id;
    private String userName;
    private String avatar;
}
