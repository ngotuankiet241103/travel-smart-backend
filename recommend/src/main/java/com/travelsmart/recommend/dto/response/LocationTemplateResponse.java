package com.travelsmart.recommend.dto.response;


import lombok.Data;

import java.util.List;
@Data
public class LocationTemplateResponse {
    private Long place_id;
    private String category;
    private String display_name;
    private String lon;
    private String lat;
    private String name;
    private LocationImageResponse thumbnail;
    private List<String> boundingbox;
    private Double starRate;
    private int timeVisit;


}
