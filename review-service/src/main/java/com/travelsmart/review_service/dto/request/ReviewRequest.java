package com.travelsmart.review_service.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
@Data
public class ReviewRequest {
    @NotEmpty(message = "CONTENT_INVALID")
    private String content;
    @Min(message = "MIN_RATING_INVALID", value = 1)
    @Max(message = "MAX_RATING_INVALID", value = 5)
    private int starRate;
    private List<Long> imageIds;
    @NotNull(message = "LOCATION_INVALID")
    private Long locationId;
}
