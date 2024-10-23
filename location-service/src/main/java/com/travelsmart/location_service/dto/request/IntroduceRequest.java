package com.travelsmart.location_service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Collection;
import java.util.List;
@Data
public class IntroduceRequest {
    @NotEmpty(message = "CONTENT_INVALID")
    private String content;
    @NotEmpty(message = "TITLE_INVALID")
    private String title;
    private List<Long> collectionIds;
    @NotNull(message = "LOCATION_INVALID")
    private Long locationId;
}
