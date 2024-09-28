package com.travelsmart.media_service.repository;

import com.travelsmart.media_service.entity.Media;
import com.travelsmart.media_service.service.FileService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media,Long> {
}
