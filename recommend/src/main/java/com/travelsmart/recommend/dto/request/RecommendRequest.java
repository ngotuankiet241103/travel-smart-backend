package com.travelsmart.recommend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@AllArgsConstructor
public class RecommendRequest {
    private String current_lon;
    private String current_lat;
}
