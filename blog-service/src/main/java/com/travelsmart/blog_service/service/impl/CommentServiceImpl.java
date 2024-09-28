package com.travelsmart.blog_service.service.impl;

import com.travelsmart.blog_service.dto.request.CommentRequest;
import com.travelsmart.blog_service.dto.request.CommentUpdateRequest;
import com.travelsmart.blog_service.dto.response.CommentResponse;
import com.travelsmart.blog_service.entity.BlogEntity;
import com.travelsmart.blog_service.entity.CommentEntity;
import com.travelsmart.blog_service.mapper.CommentMapper;
import com.travelsmart.blog_service.repository.BlogRepository;
import com.travelsmart.blog_service.repository.CommentRepository;
import com.travelsmart.blog_service.repository.httpclient.ProfileClient;
import com.travelsmart.blog_service.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;
    private final CommentMapper commentMapper;
    private final ProfileClient profileClient;
    @Override
    public CommentResponse create(Long postId,CommentRequest commentRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        CommentEntity comment = null;
        BlogEntity blog = blogRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if(commentRequest.getParentId() != null && commentRequest.getTreeId() != null){
            CommentEntity parentComment = commentRepository.findById(commentRequest.getParentId())
                    .orElseThrow(() -> new RuntimeException("Comment not exist"));
            comment = CommentEntity.builder()
                    .nodeLeft(parentComment.getNodeLeft() + 1)
                    .nodeRight(parentComment.getNodeLeft() + 2)
                    .content(commentRequest.getContent())
                    .createDate(new Date())
                    .treeId(commentRequest.getTreeId())
                    .blog(blog)
                    .userId(userId)
                    .build();
            commentRepository.updateNodeLeft(comment.getTreeId(),comment.getNodeLeft());
            commentRepository.updateNodeRight(comment.getTreeId(),comment.getNodeLeft());
        }
        else{
            comment = CommentEntity.builder()
                    .content(commentRequest.getContent())
                    .createDate(new Date())
                    .treeId(UUID.randomUUID().toString())
                    .nodeLeft(1)
                    .userId(userId)
                    .nodeRight(2)
                    .blog(blog)
                    .build();
        }
        commentRepository.save(comment);
        return mappingOne(comment);
    }

    @Override
    public List<CommentResponse> findByPostId(Long blogId) {
        List<CommentEntity> comments = commentRepository.findByBlogId(blogId);

        return mappingList(comments);
    }

    @Override
    public CommentResponse updateComment(Long id, CommentUpdateRequest commentUpdateRequest) {
        CommentEntity commentEntity =  commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not exists"));
        commentEntity.setContent(commentUpdateRequest.getContent());
        return mappingOne(commentRepository.save(commentEntity));
    }

    private CommentResponse mappingOne(CommentEntity commentEntity){
        CommentResponse commentResponse = commentMapper.toCommentResponse(commentEntity);
        commentResponse.setUser(profileClient.getUserById(commentEntity.getUserId()).getResult());
        return commentResponse;
    }
    private List<CommentResponse> mappingList(List<CommentEntity> e){
        return e.stream()
                .map(this::mappingOne)
                .toList();
    }

}
