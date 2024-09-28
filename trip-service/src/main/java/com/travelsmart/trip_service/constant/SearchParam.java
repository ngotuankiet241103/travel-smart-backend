package com.travelsmart.location_service.constant;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
public enum SearchParam {
    TEXT("text","Free-form address","Address to search. Use 'text' parameter or structured address parameters"),
    HOUSENUMBER("text","Free-form address","Address to search. Use 'text' parameter or structured address parameters"),
    STREET("street","Structured address","Street name. Structured address parameters are an alternative to the 'text' parameter."),
    POSTCODE("postcode","Structured address","Postcode. Structured address parameters are an alternative to the 'text' parameter."),
    CITY("city","Structured address","State name. Structured address parameters are an alternative to the 'text' parameter."),
    COUNTRY("country","Structured address","State name. Structured address parameters are an alternative to the 'text' parameter."),
    NAME("name","Structured address","Amenity or place name. Structured address parameters are an alternative to the 'text' parameter.");
    private String param;
    private String format;
    private String description;
}
