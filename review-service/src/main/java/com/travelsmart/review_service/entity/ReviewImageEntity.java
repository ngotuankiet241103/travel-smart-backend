package com.travelsmart.review_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review_image")
@Data
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
