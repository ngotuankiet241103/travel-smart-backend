package com.travelsmart.blog_service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
@Data
public class BlogUpdateRequest {
    @NotEmpty(message = "TITLE_INVALID")
    private String title;
    @NotEmpty(message = "CONTENT_INVALID")
    private String content;
    @NotNull(message = "TAG_INVALID")
    private List<String> tags;
    @NotNull(message = "CATEGORY_INVALID")
    private List<String> categories;
    @NotEmpty(message = "IMAGE_INVALID")
    private Long imageId;
}
