package com.travelsmart.blog_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "blog_image")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogImageEntity {
    @Id
    private Long id;
    @Column
    private String url;
}