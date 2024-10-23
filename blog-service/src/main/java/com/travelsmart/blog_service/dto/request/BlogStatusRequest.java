package com.travelsmart.blog_service.dto.request;

import com.travelsmart.blog_service.constant.BlogStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BlogStatusRequest {
    @NotEmpty(message = "STATUS_INVALID")
    private BlogStatus status;
}
