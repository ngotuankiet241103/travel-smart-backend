package com.travelsmart.recommend.dto.response.httpclient;

import com.travelsmart.recommend.constrant.LocationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendHttpResponse {
    private Long place_id;
    private String name;
    private LocationType type;
    private float score;
}
