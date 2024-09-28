package com.travelsmart.blog_service.mapper;

import com.travelsmart.blog_service.dto.response.CommentResponse;
import com.travelsmart.blog_service.entity.CommentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentResponse toCommentResponse(CommentEntity commentEntity);
}
