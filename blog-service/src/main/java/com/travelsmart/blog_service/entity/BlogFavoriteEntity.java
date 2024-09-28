package com.travelsmart.blog_service.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "blog_favorite")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogFavoriteEntity {
    @EmbeddedId
    private BlogFavoriteId id;
}
