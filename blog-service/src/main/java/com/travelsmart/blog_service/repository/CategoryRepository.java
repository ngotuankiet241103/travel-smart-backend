package com.travelsmart.blog_service.repository;

import com.travelsmart.blog_service.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
    Optional<CategoryEntity> findByName(String categoryName);

    Optional<CategoryEntity> findByCode(String categoryCode);
}
