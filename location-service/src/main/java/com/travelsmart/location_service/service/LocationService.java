package com.travelsmart.location_service.service;

import com.travelsmart.location_service.constant.LocationType;
import com.travelsmart.location_service.dto.request.*;
import com.travelsmart.location_service.dto.response.LocationImageResponse;
import com.travelsmart.location_service.dto.response.LocationResponse;
import com.travelsmart.location_service.dto.response.LocationTemplateResponse;
import com.travelsmart.location_service.dto.response.PageableResponse;
import org.locationtech.jts.io.ParseException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationService {
    List<LocationTemplateResponse> findBySearch(String search, int limit);

    LocationResponse create(LocationRequest locationRequest) throws ParseException;

    LocationTemplateResponse findByCoordinates(String lon, String lat);

    LocationImageResponse createImageLocation(ImageCreateRequest imageCreateRequest);

    void deleteImageLocation(Long id);

    List<LocationResponse> findBySearchLocal(LocationType type,String search, Pageable pageable);

    LocationResponse findByCoordinatesLocal(String lon, String lat);

    LocationResponse update(Long id,LocationUpdateRequest locationUpdateRequest);

    LocationResponse findById(Long id);

    List<LocationResponse> findNewest(int limit);

    LocationResponse createByCoordinates(LocationCoordinateRequest locationCoordinateRequest);

    LocationResponse updateStatus(Long id, LocationStatusRequest locationStatusRequest);

    List<LocationResponse> findByCategory(List<String> categories);

    PageableResponse<List<LocationResponse>> findAll(Pageable pageable);

    List<LocationResponse> findByType(Long id,List<LocationType> types);

    List<LocationResponse> findAllByType(LocationType locationType);
}
