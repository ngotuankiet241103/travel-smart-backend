package com.travelsmart.recommend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@AllArgsConstructor
public class RecommendRequest {
    @NotEmpty(message = "CURRENT_LON_INVALID")
    private String current_lon;
    @NotEmpty(message = "CURRENT_LAT_INVALID")
    private String current_lat;
}
