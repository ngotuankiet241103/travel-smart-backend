package com.travelsmart.blog_service.dto.request;

import lombok.Data;

import java.util.List;
@Data
public class BlogUpdateRequest {
    private String title;
    private String content;
    private List<String> tags;
    private List<String> categories;
}
