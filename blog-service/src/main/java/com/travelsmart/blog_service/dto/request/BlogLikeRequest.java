package com.travelsmart.blog_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BlogLikeRequest {
    @NotNull(message = "BLOG_ID_INVALID")
    private Long blogId;
}
