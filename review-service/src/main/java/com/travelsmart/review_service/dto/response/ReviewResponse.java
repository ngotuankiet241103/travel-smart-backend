package com.travelsmart.review_service.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ReviewResponse {
    private Long id;
    private int starRate;
    private List<ReviewImageResponse> images;
    private String content;
    private ProfileUserResponse user;
}
