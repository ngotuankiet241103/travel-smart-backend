package com.travelsmart.blog_service.dto.response;

import lombok.Data;

@Data
public class CommentResponse {
    private Long id;
    private String treeId;
    private int nodeLeft;
    private int nodeRight;
    private ProfileUserResponse user;
    private String content;
}
