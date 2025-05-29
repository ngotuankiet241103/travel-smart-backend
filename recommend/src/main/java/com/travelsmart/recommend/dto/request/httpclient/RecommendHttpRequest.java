package com.travelsmart.recommend.dto.request.httpclient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RecommendHttpRequest {
    private float current_lat;
    private float current_lng;
    private int tourist_attraction;
    private int restaurant;
    private int shopping;
    private int park;
    private int culture_site;
    private int accommodation;
    private int entertainment;


}
