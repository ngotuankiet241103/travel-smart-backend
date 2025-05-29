package com.travelsmart.recommend.dto.response;

import com.travelsmart.recommend.constrant.LocationType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LocationResponse extends LocationTemplateResponse {
    private LocationType type;
    private LocationImageResponse thumbnail;
    private int timeVisit;
}
