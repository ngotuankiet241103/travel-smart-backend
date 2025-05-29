package com.travelsmart.review_service.dto.response.httpclient;

import lombok.Data;

@Data
public class LocationImageResponse {
    private Long id;
    private String url;
    private String caption;
}
