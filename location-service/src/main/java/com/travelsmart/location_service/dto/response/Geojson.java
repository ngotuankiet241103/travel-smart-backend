package com.travelsmart.location_service.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class Geojson  {
    private String type;
    private Object[][] coordinates;
}
