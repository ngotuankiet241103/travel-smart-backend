package com.travelsmart.blog_service.controller;

import com.travelsmart.blog_service.dto.request.CategoryRequest;
import com.travelsmart.blog_service.dto.response.ApiResponse;
import com.travelsmart.blog_service.dto.response.BlogResponse;
import com.travelsmart.blog_service.dto.response.CategoryResponse;
import com.travelsmart.blog_service.dto.response.PageableResponse;
import com.travelsmart.blog_service.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @Operation(summary = "Create new category of blog",description = "Returns single category")
    @PostMapping
    public ApiResponse<CategoryResponse> create(@Valid @RequestBody CategoryRequest categoryRequest){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.create(categoryRequest))
                .build();
    }
    @Operation(summary = "Get categories",description = "Returns categories")
    @GetMapping
    public ApiResponse<PageableResponse<List<CategoryResponse>>> getCategories(@RequestParam(value = "page",defaultValue = "1") int page,
                                                                    @RequestParam(value = "limit",defaultValue = "5") int limit){
        Pageable pageable = PageRequest.of(page - 1,limit);
        return ApiResponse.<PageableResponse<List<CategoryResponse>>>builder()
                .result(categoryService.findAll(pageable))
                .build();
    }
    @Operation(summary = "Get all category",description = "Returns categories")
    @GetMapping("/all")
    public ApiResponse<List<CategoryResponse>> getAll(){

        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.findAll())
                .build();
    }
}
