package com.travelsmart.review_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review_image")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewImageEntity {
    @Id
    private Long id;
    @Column
    private String url;
    @ManyToOne
    private ReviewEntity review;
}
