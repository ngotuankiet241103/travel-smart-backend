package com.travelsmart.blog_service.dto.request;

import lombok.Data;

@Data
public class CommentRequest {
    private String content;
    private String treeId;
    private Long parentId;
}
