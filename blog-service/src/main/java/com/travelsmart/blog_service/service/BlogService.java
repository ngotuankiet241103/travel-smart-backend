package com.travelsmart.blog_service.service;

import com.travelsmart.blog_service.dto.request.BlogLikeRequest;
import com.travelsmart.blog_service.dto.request.BlogRequest;
import com.travelsmart.blog_service.dto.request.BlogUpdateRequest;
import com.travelsmart.blog_service.dto.response.BlogResponse;
import com.travelsmart.blog_service.dto.response.PageableResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {
    BlogResponse create(BlogRequest blogRequest);

    PageableResponse<List<BlogResponse>> findAll(Pageable pageable);

    PageableResponse<List<BlogResponse>> findByCategoryCode(String categoryCode,
                                                            Pageable pageable);

    BlogResponse update(Long blogId, BlogUpdateRequest blogUpdateRequest);

    void deleteById(Long blogId);

    BlogResponse likePost(BlogLikeRequest blogLikeRequest);

    BlogResponse unlikePost(BlogLikeRequest blogLikeRequest);

    BlogResponse findByCode(String blogCode);
}
