package com.travelsmart.review_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "review")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity {
    @Column
    private String content;
    @OneToMany(mappedBy = "review")
    private List<ReviewImageEntity> images;
    private int starRate;
}
