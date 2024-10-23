package com.travelsmart.blog_service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CategoryRequest {
    @NotEmpty(message = "NAME_INVALID")
    private String name;
}
