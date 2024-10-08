package com.travelsmart.blog_service.service;

import com.travelsmart.blog_service.dto.request.CategoryRequest;
import com.travelsmart.blog_service.dto.response.CategoryResponse;
import com.travelsmart.blog_service.dto.response.PageableResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService 
{
    CategoryResponse create(CategoryRequest categoryRequest);

    PageableResponse<List<CategoryResponse>> findAll(Pageable pageable);
    List<CategoryResponse> findAll();
}
