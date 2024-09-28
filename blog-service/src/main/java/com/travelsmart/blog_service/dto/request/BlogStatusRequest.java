package com.travelsmart.blog_service.dto.request;

import com.travelsmart.blog_service.constant.BlogStatus;
import lombok.Data;

@Data
public class BlogStatusRequest {
    private BlogStatus status;
}
