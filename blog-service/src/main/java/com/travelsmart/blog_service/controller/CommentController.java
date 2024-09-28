package com.travelsmart.blog_service.controller;

import com.travelsmart.blog_service.dto.request.CommentRequest;
import com.travelsmart.blog_service.dto.request.CommentUpdateRequest;
import com.travelsmart.blog_service.dto.response.ApiResponse;
import com.travelsmart.blog_service.dto.response.CommentResponse;
import com.travelsmart.blog_service.dto.response.PageableResponse;
import com.travelsmart.blog_service.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/{post-id}/post")
    public ApiResponse<CommentResponse> create(@PathVariable("post-id") Long postId, @RequestBody CommentRequest commentRequest){
        return ApiResponse.<CommentResponse>builder()
                .result(commentService.create(postId,commentRequest))
                .build();
    }
    @GetMapping("/{post-id}/blog")
    public ApiResponse<List<CommentResponse>> getByPostId(@PathVariable("post-id") Long blogId){
        return ApiResponse.<List<CommentResponse>>builder()
                .result(commentService.findByPostId(blogId))
                .build();
    }
    @PutMapping("/{id}")
    public ApiResponse<CommentResponse> updateComment(@PathVariable("id") Long id,
                                                      @RequestBody CommentUpdateRequest commentUpdateRequest){
        return ApiResponse.<CommentResponse>builder()
                .result(commentService.updateComment(id,commentUpdateRequest))
                .build();
    }

}
