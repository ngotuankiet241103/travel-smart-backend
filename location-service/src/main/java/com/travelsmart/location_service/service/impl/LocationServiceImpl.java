package com.travelsmart.location_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelsmart.location_service.constant.LocationStatus;
import com.travelsmart.location_service.constant.LocationType;
import com.travelsmart.location_service.dto.request.*;
import com.travelsmart.location_service.dto.response.*;
import com.travelsmart.location_service.dto.response.http.MediaHttpResponse;
import com.travelsmart.location_service.entity.LocationEntity;
import com.travelsmart.location_service.entity.LocationImageEntity;
import com.travelsmart.location_service.exception.CustomRuntimeException;
import com.travelsmart.location_service.exception.ErrorCode;
import com.travelsmart.location_service.mapper.LocationImageMapper;
import com.travelsmart.location_service.mapper.LocationMapper;
import com.travelsmart.location_service.repository.LocationImageRepository;
import com.travelsmart.location_service.repository.LocationRepository;
import com.travelsmart.location_service.repository.httpclient.LocationClient;
import com.travelsmart.location_service.repository.httpclient.MediaClient;
import com.travelsmart.location_service.repository.httpclient.ReviewClient;
import com.travelsmart.location_service.service.LocationService;
import com.travelsmart.location_service.utils.ConvertUtils;
import com.travelsmart.location_service.utils.GeoJsonConverter;
import com.travelsmart.location_service.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Location;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationClient locationClient;
    private final LocationImageRepository locationImageRepository;
    private final ObjectMapper objectMapper;
    private final LocationMapper locationMapper;
    private final LocationImageMapper locationImageMapper;
    private final LocationRepository locationRepository;
    private final MediaClient mediaClient;
    private final ReviewClient reviewClient;
    @Override
    public List<LocationTemplateResponse> findBySearch(String search, int limit) {
        return locationClient.getBySearch(search,limit)
                .stream()
                .map(object -> ConvertUtils.convert(object, LocationTemplateResponse.class))
                .toList();
    }

    @Override
    public LocationResponse create(LocationRequest locationRequest) throws ParseException {
        LocationEntity location = locationMapper.toLocationEntity(locationRequest);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities()
                .stream().anyMatch(role -> role.toString().equals("ROLE_ADMIN"));


        location.setStatus(isAdmin ?  LocationStatus.ACCEPT : LocationStatus.PENDING);
        location.setTimeVisit(locationRequest.getType().getTimeToVisitMinutes());

        if(locationRequest.getImageId() != null){
            LocationImageEntity locationImageEntity = locationImageRepository.findById(locationRequest.getImageId())
                    .orElseThrow(() -> new CustomRuntimeException(ErrorCode.IMAGE_NOT_FOUND));
            location.setThumbnail(locationImageEntity);
        }

        locationRepository.save(location);




        return mappingOne(location);
    }
    private LocationResponse mappingOne(LocationEntity locationEntity){

        if(locationEntity  == null) return null;
        LocationResponse locationResponse = locationMapper.toLocationResponse(locationEntity);
        if(locationEntity.getThumbnail() != null){
            locationResponse.setThumbnail(locationImageMapper.toLocationImageResponse(locationEntity.getThumbnail()));
        }


        locationResponse.setStarRate(reviewClient.getStarRateByLocation(locationEntity.getPlace_id()).getResult());
        return locationResponse;
    }

    @Override
    public LocationTemplateResponse findByCoordinates(String lon, String lat) {
        return ConvertUtils.convert(locationClient.getByCoordinates(lon,lat),LocationTemplateResponse.class);
    }

    @Override
    public LocationImageResponse createImageLocation(ImageCreateRequest imageCreateRequest) {
        MediaHttpResponse mediaHttpResponse = mediaClient.uploadFile(imageCreateRequest.getFile()).getResult();
        LocationImageEntity locationImageEntity = LocationImageEntity.builder()
                .url(mediaHttpResponse.getUrl())
                .caption(mediaHttpResponse.getCaption())
                .id(mediaHttpResponse.getId())
                .build();
        return locationImageMapper.toLocationImageResponse(locationImageRepository.save(locationImageEntity));
    }

    @Override
    public void deleteImageLocation(Long id) {
        LocationImageEntity locationImageEntity = locationImageRepository.findById(id)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.IMAGE_NOT_FOUND));
        mediaClient.deleteById(locationImageEntity.getId());
        locationImageRepository.deleteById(id);
    }

    @Override
    public List<LocationResponse> findBySearchLocal(LocationType type,String search, Pageable pageable) {
        if( type == LocationType.ADMINISTRATIVE){
            return mappingList(locationRepository.findByTypeAndStatus(LocationType.ADMINISTRATIVE,LocationStatus.ACCEPT));
        }
        search = StringUtils.buildParamSearch(search.trim());
      return mappingList(locationRepository.findBySearchParam(pageable,search, LocationStatus.ACCEPT));
    }

    @Override
    public LocationResponse findByCoordinatesLocal(String lon, String lat) {
        return mappingOne(locationRepository.findByCoordinates(lon,lat));
    }



    @Override
    public LocationResponse update(Long placeId, LocationUpdateRequest locationUpdateRequest) {

        LocationEntity location = locationRepository.findById(placeId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.LOCATION_NOT_FOUND));
        LocationImageEntity thumbnail = location.getThumbnail();

        location = locationMapper.toLocationEntity(locationUpdateRequest);
        location.setPlace_id(placeId);
        if(locationUpdateRequest.getImageId() != null){
            if(thumbnail == null){
                LocationImageEntity locationImageEntity = locationImageRepository.findById(locationUpdateRequest.getImageId())
                        .orElseThrow(() -> new CustomRuntimeException(ErrorCode.IMAGE_NOT_FOUND));
                location.setThumbnail(locationImageEntity);
            }
            else{
               if (!thumbnail.getId().equals(locationUpdateRequest.getImageId())){
                   LocationImageEntity locationImageEntity = locationImageRepository.findById(locationUpdateRequest.getImageId())
                           .orElseThrow(() -> new CustomRuntimeException(ErrorCode.IMAGE_NOT_FOUND));
                   location.setThumbnail(locationImageEntity);
                   locationRepository.save(location);
                   locationImageRepository.deleteById(location.getThumbnail().getId());

               }
            }
        }



        return mappingOne(location);
    }

    @Override
    public LocationResponse findById(Long id) {
        LocationEntity location = locationRepository.findByPlaceId(id).orElse(null);
        System.out.println("location" + location);
        return mappingOne(locationRepository.findByPlaceId(id)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.LOCATION_NOT_FOUND)));
    }

    @Override
    public List<LocationResponse> findNewest(int limit) {
        Pageable pageable = PageRequest.of(0,limit);
        return mappingList(locationRepository.findAllOrderByIdDesc(pageable).getContent());
    }

    @Override
    public LocationResponse createByCoordinates(LocationCoordinateRequest locationCoordinateRequest) {
        LocationTemplateResponse locationTemplateResponse =  ConvertUtils
                .convert(locationClient.
                        getByCoordinates(locationCoordinateRequest.getLon(),
                                        locationCoordinateRequest.getLat()),
                                        LocationTemplateResponse.class);

        LocationEntity location = locationMapper.toLocationEntity(locationTemplateResponse);
        if(location == null){
            return null;
        }
        location.setStatus(LocationStatus.PENDING);
        if(locationCoordinateRequest.getImageId() != null){
            LocationImageEntity locationImageEntity = locationImageRepository.findById(locationCoordinateRequest.getImageId())
                    .orElseThrow(() -> new CustomRuntimeException(ErrorCode.IMAGE_NOT_FOUND));
            location.setThumbnail(locationImageEntity);
        }
        location.setType(locationCoordinateRequest.getType());


        return mappingOne(locationRepository.save(location));
    }

    @Override
    public LocationResponse updateStatus(Long id, LocationStatusRequest locationStatusRequest) {
       LocationEntity location = locationRepository.findById(id)
               .orElseThrow(() -> new CustomRuntimeException(ErrorCode.LOCATION_NOT_FOUND));
       location.setStatus(locationStatusRequest.getStatus());
       return mappingOne(locationRepository.save(location));
    }

    @Override
    public List<LocationResponse> findByCategory(List<String> categories) {

        return mappingList(locationRepository.findByCategoryIn(categories));
    }

    @Override
    public PageableResponse<List<LocationResponse>> findAll(Pageable pageable) {
        Page<LocationEntity> page = locationRepository.findAll(pageable);
        Paging paging = Paging.buildPaging(pageable, page.getTotalPages());
        PageableResponse<List<LocationResponse>> response = PageableResponse.<List<LocationResponse>>builder()
                .data(mappingList(page.getContent()))
                .paging(paging)
                .build();
        return response;
    }

    @Override
    public List<LocationResponse> findByType(Long id ,List<LocationType> types) {
        return mappingList(locationRepository.findByTypeIn(id,types));
    }

    @Override
    public List<LocationResponse> findAllByType(LocationType locationType) {
        return mappingList(locationRepository.findByType(locationType));
    }

    private List<LocationResponse> mappingList(List<LocationEntity> e){
        System.out.println(e.size());
        return e.stream()
                .map(this::mappingOne)
                .toList();
    }
}
