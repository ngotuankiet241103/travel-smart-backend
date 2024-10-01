package com.travelsmart.location_service.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class IntroduceUpdateRequest {
    private String title;
    private String content;
    private List<Long> collectionIds;
}
