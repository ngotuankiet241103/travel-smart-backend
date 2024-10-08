package com.travelsmart.blog_service.dto.response;

import com.travelsmart.blog_service.constant.BlogStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BlogResponse {
    private Long id;
    private String title;
    private String code;
    private String content;
    private Date createDate;
    private List<String> tags;
    private List<String> categories;
    private String userName;
    private Long totalLike;
    private BlogStatus status;
    private BlogImageResponse thumbnail;
}
