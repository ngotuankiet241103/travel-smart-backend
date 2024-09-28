package com.travelsmart.blog_service.repository;

import com.travelsmart.blog_service.entity.BlogFavoriteEntity;
import com.travelsmart.blog_service.entity.BlogFavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogFavoriteRepository extends JpaRepository<BlogFavoriteEntity, BlogFavoriteId> {
    Long countByIdBlogId(Long id);
}
