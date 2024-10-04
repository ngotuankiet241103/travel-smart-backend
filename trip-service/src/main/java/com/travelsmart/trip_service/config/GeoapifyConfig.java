package com.travelsmart.trip_service.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j

public class GeoapifyConfig implements RequestInterceptor {
    @Value("${geoapify.key}")
    private String apiKey;
    @Override
    public void apply(RequestTemplate template) {
        template.query("apiKey",apiKey);
        template.query("mode","drive");
    }
}
