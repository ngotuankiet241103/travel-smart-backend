package com.travelsmart.blog_service.mapper;

import com.travelsmart.blog_service.dto.request.BlogRequest;
import com.travelsmart.blog_service.dto.response.BlogResponse;
import com.travelsmart.blog_service.entity.BlogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BlogMapper {
    @Mapping(target = "categories",ignore = true)
    BlogEntity toBlogEntity(BlogRequest blogRequest);
    @Mapping(target = "categories",ignore = true)
    BlogResponse toBLogResponse(BlogEntity blogEntity);
}
