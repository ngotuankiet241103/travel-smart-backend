package com.travelsmart.location_service.service;

import com.travelsmart.location_service.constant.LocationType;
import com.travelsmart.location_service.dto.request.*;
import com.travelsmart.location_service.dto.request.ReportType;
import com.travelsmart.location_service.dto.response.*;
import org.locationtech.jts.io.ParseException;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

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

    PageableResponse<List<LocationResponse>> findAll(LocationType type,String search,Pageable pageable);

    List<LocationResponse> findByType(Long id,List<LocationType> types,int day);

    List<LocationResponse> findAllByType(LocationType locationType);

    List<LocationResponse> findByRadius(String lon, String lat, double radius, String search, LocationType type);

    LocationReport getReport(ReportType type,Integer year,Integer month);

    Map<String, Object> getStatistics();
}
