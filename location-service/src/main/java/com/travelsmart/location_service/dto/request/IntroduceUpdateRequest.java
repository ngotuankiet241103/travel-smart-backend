package com.travelsmart.location_service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class IntroduceUpdateRequest {
    @NotEmpty(message = "CONTENT_INVALID")
    private String content;
    @NotEmpty(message = "TITLE_INVALID")
    private String title;
    private List<Long> collectionIds;
}
