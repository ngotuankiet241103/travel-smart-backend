package com.travelsmart.location_service.dto.response;

import lombok.Data;

import java.util.List;
@Data
public class IntroduceResponse {
    private Long id;
    private String title;
    private String content;
    private List<LocationImageResponse> collections;
}
