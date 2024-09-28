package com.travelsmart.location_service.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class LocationInterceptor implements RequestInterceptor {
    @Value("${nominatim.version}")
    private String format;
    @Override
    public void apply(RequestTemplate template) {
       template.query("format",format);
       template.query("addressdetails", String.valueOf(1));
    }
}
