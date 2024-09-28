package com.travelsmart.location_service.repository.httpclient;

import com.travelsmart.location_service.config.LocationInterceptor;
import com.travelsmart.location_service.dto.response.LocationResponse;
import com.travelsmart.location_service.entity.LocationEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "profile-service", url = "${nominatim.url}",
        configuration = { LocationInterceptor.class })
public interface LocationClient {
    @GetMapping("${nominatim.search}")
    List<Object> getBySearch(@RequestParam("q") String search, @RequestParam("limit") int limit);
    @GetMapping("${nominatim.reverse}")
    Object getByCoordinates(@RequestParam("lon") String lon,@RequestParam("lat") String lat);
}
