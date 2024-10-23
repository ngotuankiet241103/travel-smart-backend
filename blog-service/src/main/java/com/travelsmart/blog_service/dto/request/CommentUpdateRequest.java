package com.travelsmart.blog_service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CommentUpdateRequest {
    @NotEmpty(message = "CONTENT_INVALID")
    private String content;
}
