package com.travelsmart.review_service.dto.request;

import lombok.Data;

import java.util.List;
@Data
public class ReviewUpdateRequest {
    private Long locationId;
    private String content;
    private int starRate;
    private List<Long> imageIds;
}
