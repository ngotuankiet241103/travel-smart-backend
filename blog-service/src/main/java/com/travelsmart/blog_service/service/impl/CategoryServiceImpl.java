package com.travelsmart.blog_service.service.impl;

import com.travelsmart.blog_service.dto.request.CategoryRequest;
import com.travelsmart.blog_service.dto.response.BlogResponse;
import com.travelsmart.blog_service.dto.response.CategoryResponse;
import com.travelsmart.blog_service.dto.response.PageableResponse;
import com.travelsmart.blog_service.dto.response.Paging;
import com.travelsmart.blog_service.entity.BlogEntity;
import com.travelsmart.blog_service.entity.CategoryEntity;
import com.travelsmart.blog_service.repository.CategoryRepository;
import com.travelsmart.blog_service.service.CategoryService;
import com.travelsmart.blog_service.utils.HandleString;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse create(CategoryRequest categoryRequest) {
        CategoryEntity category = CategoryEntity.builder()
                .name(categoryRequest.getName())
                .code(HandleString.strToCode(categoryRequest.getName()))
                .build();
        return mappingOne(categoryRepository.save(category));
    }
    @Override
    public PageableResponse<List<CategoryResponse>> findAll(Pageable pageable) {
        Page<CategoryEntity> page = categoryRepository.findAll(pageable);
        Paging paging = Paging.buildPaging(pageable,page.getTotalPages());
        PageableResponse<List<CategoryResponse>> response = PageableResponse.<List<CategoryResponse>>builder()
                .data(mappingList(page.getContent()))
                .paging(paging)
                .build();
        return response;
    }
    private List<CategoryResponse> mappingList(List<CategoryEntity> e){

        return e.stream()
                .map(this::mappingOne)
                .toList();
    }
    private CategoryResponse mappingOne(CategoryEntity categoryEntity){
        return CategoryResponse.builder()
                .name(categoryEntity.getName())
                .code(categoryEntity.getCode())
                .build();
    }
}
