package com.travelsmart.blog_service.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class BlogImageRequest {

    private MultipartFile file;
}
