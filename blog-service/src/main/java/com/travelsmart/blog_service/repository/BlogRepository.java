package com.travelsmart.blog_service.repository;

import com.travelsmart.blog_service.entity.BlogEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BlogRepository extends JpaRepository<BlogEntity,Long> {
    @Query(value = "SELECT b.id,b.title,b.code,b.create_date,b.userName,c.* FROM blog b JOIN blog_category c ON b.id = c.blog_id WHERE c.category_name = ?1",nativeQuery = true)
    Page<BlogEntity> findByCategoryName(Pageable pageable,String categoryName);

    Optional<BlogEntity> findByCode(String blogCode);

}