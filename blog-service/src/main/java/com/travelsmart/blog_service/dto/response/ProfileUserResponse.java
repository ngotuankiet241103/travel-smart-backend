package com.travelsmart.blog_service.dto.response;

import lombok.Data;

@Data
public class ProfileUserResponse {
    private Long id;
    private String userName;
    private String avatar;
}
