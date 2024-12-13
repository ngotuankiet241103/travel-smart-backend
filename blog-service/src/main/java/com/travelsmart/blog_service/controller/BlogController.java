package com.travelsmart.blog_service.controller;

import com.travelsmart.blog_service.constant.BlogStatus;
import com.travelsmart.blog_service.dto.request.*;
import com.travelsmart.blog_service.dto.response.ApiResponse;
import com.travelsmart.blog_service.dto.response.BlogImageResponse;
import com.travelsmart.blog_service.dto.response.BlogResponse;
import com.travelsmart.blog_service.dto.response.PageableResponse;
import com.travelsmart.blog_service.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;
    @Operation(summary = "Create new blog",description = "Returns single blog")
    @PostMapping
    public ApiResponse<BlogResponse> create(@Valid @RequestBody BlogRequest blogRequest){
        return ApiResponse.<BlogResponse>builder()
                .result(blogService.create(blogRequest))
                .build();

    }
    @Operation(summary = "Get blogs ",description = "Returns list blog")
    @GetMapping
    public ApiResponse<PageableResponse<List<BlogResponse>>> getAll( @RequestParam("q") String search, @RequestParam(value = "page",defaultValue = "1") int page,
                                                @RequestParam(value = "limit",defaultValue = "5") int limit){
        Pageable pageable = PageRequest.of(page - 1,limit);
        return ApiResponse.<PageableResponse<List<BlogResponse>>>builder()
                .result(blogService.findAll(search,pageable))
                .build();
    }
    @Operation(summary = "Get blogs by category",description = "Returns list blog")
    @GetMapping("/{category-code}/category")
    public ApiResponse<PageableResponse<List<BlogResponse>>> getByCategoryCode(
            @PathVariable("category-code") String categoryName,
            @RequestParam(value = "page",defaultValue = "1") int page,
            @RequestParam(value = "limit",defaultValue = "5") int limit){
        Pageable pageable = PageRequest.of(page - 1,limit);
        return ApiResponse.<PageableResponse<List<BlogResponse>>>builder()
                .result(blogService.findByCategoryCode(categoryName,pageable))
                .build();
    }
    @Operation(summary = "Get blog by code",description = "Returns single blog")
    @GetMapping("/{blog-code}")
    public ApiResponse<BlogResponse> getBlogByCode(@PathVariable("blog-code") String blogCode){
        return ApiResponse.<BlogResponse>builder()
                .result(blogService.findByCode(blogCode))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get statuses of blog",description = "Returns statues of blog")
    @GetMapping("/status")
    public  ApiResponse<List<BlogStatus>> getBlogStatuses(){
        return ApiResponse.<List<BlogStatus>>builder()
                .result(BlogStatus.getBlogStatues())
                .build();
    }
    @Operation(summary = "Update blog ",description = "Returns single blog")
    @PutMapping("/{blog-id}")
    public ApiResponse<BlogResponse> update(@PathVariable("blog-id") Long blogId,
                                            @Valid @RequestBody BlogUpdateRequest blogUpdateRequest){
        return ApiResponse.<BlogResponse>builder()
                .result(blogService.update(blogId,blogUpdateRequest))
                .build();
    }

    @Operation(summary = "Like a blog",description = "Returns single blog")
    @PostMapping("/like")
    public ApiResponse<BlogResponse> likePost(@Valid @RequestBody BlogLikeRequest blogLikeRequest){
        return ApiResponse.<BlogResponse>builder()
                .result(blogService.likePost(blogLikeRequest))
                .build();
    }
    @Operation(summary = "Unlike a blog",description = "Returns single blog")
    @PutMapping("/unlike")
    public ApiResponse<BlogResponse> unlikePost(@Valid @RequestBody BlogLikeRequest blogLikeRequest){
        return ApiResponse.<BlogResponse>builder()
                .result(blogService.unlikePost(blogLikeRequest))
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update status blog",description = "Returns single blog")
    @PutMapping("/status/{blog-id}")
    public ApiResponse<BlogResponse> updateStatus(@PathVariable("blog-id") Long id,
                                                  @Valid @RequestBody BlogStatusRequest blogStatusRequest){
        return ApiResponse.<BlogResponse>builder()
                .result(blogService.updateStatus(id,blogStatusRequest))
                .build();
    }
    @Operation(summary = "Upload image of blog",description = "Returns single image")
    @PostMapping("/image")
    public ApiResponse<BlogImageResponse> uploadImage(@ModelAttribute BlogImageRequest blogImageRequest){
        return ApiResponse.<BlogImageResponse>builder()
                .result(blogService.uploadImage(blogImageRequest))
                .build();
    }
    @Operation(summary = "Delete image of blog" )
    @DeleteMapping("/image/{image-id}")
    public ApiResponse<Void> deleteImage(@PathVariable("image-id") Long id){
        blogService.deleteImage(id);
        return ApiResponse.<Void>builder()
                .build();
    }
    @Operation(summary = "Delete blog by id" )
    @DeleteMapping("/{blog-id}")
    public ApiResponse<String> deleteBlogById(@PathVariable("blog-id") Long id){

        return ApiResponse.<String>builder()
                .result(blogService.deleteBlogById(id))
                .build();
    }



}
