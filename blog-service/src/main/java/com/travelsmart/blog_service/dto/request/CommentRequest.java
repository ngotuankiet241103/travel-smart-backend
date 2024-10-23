package com.travelsmart.blog_service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CommentRequest {
    @NotEmpty(message = "CONTENT_INVALID")
    private String content;
    private String treeId;
    private Long parentId;
}
