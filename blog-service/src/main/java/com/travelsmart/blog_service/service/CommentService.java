package com.travelsmart.blog_service.service;

import com.travelsmart.blog_service.dto.request.CommentRequest;
import com.travelsmart.blog_service.dto.request.CommentUpdateRequest;
import com.travelsmart.blog_service.dto.response.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentResponse create(Long postId,CommentRequest commentRequest);

    List<CommentResponse> findByPostId(Long blogId);

    CommentResponse updateComment(Long id, CommentUpdateRequest commentUpdateRequest);
}
