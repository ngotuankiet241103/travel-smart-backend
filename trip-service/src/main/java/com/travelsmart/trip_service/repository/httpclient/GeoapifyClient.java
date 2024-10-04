package com.travelsmart.trip_service.repository.httpclient;

import com.travelsmart.trip_service.config.GeoapifyConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "geoapify-service", url = "${geoapify.url}",configuration = {GeoapifyConfig.class})
public interface GeoapifyClient {
    @GetMapping("${geoapify.routing}")
    Map<String,Object> routingLocation(@RequestParam("waypoints") String waypoints);
}
