package com.travelsmart.location_service.service;

import com.travelsmart.location_service.dto.request.ImageCreateRequest;
import com.travelsmart.location_service.dto.request.LocationRequest;
import com.travelsmart.location_service.dto.request.LocationUpdateRequest;
import com.travelsmart.location_service.dto.response.LocationImageResponse;
import com.travelsmart.location_service.dto.response.LocationResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationService {
    List<LocationResponse> findBySearch(String search, int limit);

    LocationResponse create(LocationRequest locationRequest);

    LocationResponse findByCoordinates(String lon, String lat);

    LocationImageResponse createImageLocation(ImageCreateRequest imageCreateRequest);

    void deleteImageLocation(Long id);

    List<LocationResponse> findBySearchLocal(String search, Pageable pageable);

    LocationResponse findByCoordinatesLocal(String lon, String lat);

    LocationResponse update(Long id,LocationUpdateRequest locationUpdateRequest);

    LocationResponse findById(Long id);
}
