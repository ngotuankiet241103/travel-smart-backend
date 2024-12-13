package com.travelsmart.blog_service.service;

import com.travelsmart.blog_service.dto.request.*;
import com.travelsmart.blog_service.dto.response.BlogImageResponse;
import com.travelsmart.blog_service.dto.response.BlogResponse;
import com.travelsmart.blog_service.dto.response.PageableResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {
    BlogResponse create(BlogRequest blogRequest);

    PageableResponse<List<BlogResponse>> findAll(String search,Pageable pageable);

    PageableResponse<List<BlogResponse>> findByCategoryCode(String categoryCode,
                                                            Pageable pageable);

    BlogResponse update(Long blogId, BlogUpdateRequest blogUpdateRequest);

    void deleteById(Long blogId);

    BlogResponse likePost(BlogLikeRequest blogLikeRequest);

    BlogResponse unlikePost(BlogLikeRequest blogLikeRequest);

    BlogResponse findByCode(String blogCode);

    BlogResponse updateStatus(Long id, BlogStatusRequest blogStatusRequest);

    BlogImageResponse uploadImage(BlogImageRequest blogImageRequest);

    void deleteImage(Long id);

    String deleteBlogById(Long id);
}
