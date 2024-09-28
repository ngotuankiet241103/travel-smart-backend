package com.travelsmart.blog_service.repository;

import com.travelsmart.blog_service.entity.BlogImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogImageRepository extends JpaRepository<BlogImageEntity,Long> {
}
