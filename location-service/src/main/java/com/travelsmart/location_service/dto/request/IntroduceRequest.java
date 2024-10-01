package com.travelsmart.location_service.dto.request;

import lombok.Data;

import java.util.Collection;
import java.util.List;
@Data
public class IntroduceRequest {
    private String content;
    private String title;
    private List<Long> collectionIds;
    private Long locationId;
}
