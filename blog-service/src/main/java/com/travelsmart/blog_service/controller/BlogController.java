package com.travelsmart.blog_service.controller;

import com.travelsmart.blog_service.dto.request.BlogLikeRequest;
import com.travelsmart.blog_service.dto.request.BlogRequest;
import com.travelsmart.blog_service.dto.request.BlogUpdateRequest;
import com.travelsmart.blog_service.dto.response.ApiResponse;
import com.travelsmart.blog_service.dto.response.BlogResponse;
import com.travelsmart.blog_service.dto.response.PageableResponse;
import com.travelsmart.blog_service.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/blogs")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;
    @PostMapping
    public ApiResponse<BlogResponse> create(@RequestBody BlogRequest blogRequest){
        return ApiResponse.<BlogResponse>builder()
                .result(blogService.create(blogRequest))
                .build();

    }
    @GetMapping
    public ApiResponse<PageableResponse<List<BlogResponse>>> getAll(@RequestParam(value = "page",defaultValue = "1") int page,
                                                @RequestParam(value = "limit",defaultValue = "5") int limit){
        Pageable pageable = PageRequest.of(page - 1,limit);
        return ApiResponse.<PageableResponse<List<BlogResponse>>>builder()
                .result(blogService.findAll(pageable))
                .build();
    }
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
    @GetMapping("/{blog-code}")
    public ApiResponse<BlogResponse> getBlogByCode(@PathVariable("blog-code") String blogCode){
        return ApiResponse.<BlogResponse>builder()
                .result(blogService.findByCode(blogCode))
                .build();
    }

    @PutMapping("/{blog-id}")
    public ApiResponse<BlogResponse> update(@PathVariable("blog-id") Long blogId,
                                            @RequestBody BlogUpdateRequest blogUpdateRequest){
        return ApiResponse.<BlogResponse>builder()
                .result(blogService.update(blogId,blogUpdateRequest))
                .build();
    }
    @DeleteMapping("/{blog-id}")
    public ApiResponse<Void> deleteById(@PathVariable("blog-id") Long blogId){
        blogService.deleteById(blogId);
        return ApiResponse.<Void>builder().build();
    }
    @PostMapping("/like")
    public ApiResponse<BlogResponse> likePost(@RequestBody BlogLikeRequest blogLikeRequest){
        return ApiResponse.<BlogResponse>builder()
                .result(blogService.likePost(blogLikeRequest))
                .build();
    }
    @PutMapping("/unlike")
    public ApiResponse<BlogResponse> unlikePost(@RequestBody BlogLikeRequest blogLikeRequest){
        return ApiResponse.<BlogResponse>builder()
                .result(blogService.unlikePost(blogLikeRequest))
                .build();
    }

}
