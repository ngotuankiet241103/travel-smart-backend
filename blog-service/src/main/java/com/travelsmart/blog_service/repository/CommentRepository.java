package com.travelsmart.blog_service.repository;

import com.travelsmart.blog_service.entity.CommentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity,Long> {
    @Transactional
    @Modifying
    @Query("UPDATE CommentEntity c SET c.nodeLeft = c.nodeLeft + 2 WHERE c.treeId = ?1 AND c.nodeLeft >= ?2 ")
    void updateNodeLeft(String treeId,int nodeLeft);
    @Transactional
    @Modifying
    @Query("UPDATE CommentEntity c SET c.nodeRight = c.nodeRight + 2 WHERE c.treeId = ?1 AND c.nodeRight >= ?2 ")
    void updateNodeRight(String treeId, int nodeLeft);

    List<CommentEntity> findByBlogId(Long blogId);
}
