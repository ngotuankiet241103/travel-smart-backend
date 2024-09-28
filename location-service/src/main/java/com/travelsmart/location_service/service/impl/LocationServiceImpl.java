package com.travelsmart.location_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelsmart.location_service.constant.LocationStatus;
import com.travelsmart.location_service.dto.request.ImageCreateRequest;
import com.travelsmart.location_service.dto.request.LocationRequest;
import com.travelsmart.location_service.dto.request.LocationUpdateRequest;
import com.travelsmart.location_service.dto.response.LocationImageResponse;
import com.travelsmart.location_service.dto.response.LocationResponse;
import com.travelsmart.location_service.dto.response.http.MediaHttpResponse;
import com.travelsmart.location_service.entity.LocationEntity;
import com.travelsmart.location_service.entity.LocationImageEntity;
import com.travelsmart.location_service.mapper.LocationImageMapper;
import com.travelsmart.location_service.mapper.LocationMapper;
import com.travelsmart.location_service.repository.LocationImageRepository;
import com.travelsmart.location_service.repository.LocationRepository;
import com.travelsmart.location_service.repository.httpclient.LocationClient;
import com.travelsmart.location_service.repository.httpclient.MediaClient;
import com.travelsmart.location_service.service.LocationService;
import com.travelsmart.location_service.utils.StringUtils;
import lombok.RequiredArgsConstructor;
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
    @Override
    public List<LocationResponse> findBySearch(String search, int limit) {
        return locationClient.getBySearch(search,limit)
                .stream()
                .map(object -> com.travel.travel.utils.ConvertUtils.convert(object,LocationResponse.class))
                .toList();
    }

    @Override
    public LocationResponse create(LocationRequest locationRequest) {
        LocationEntity location = locationMapper.toLocationEntity(locationRequest);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities()
                .stream().anyMatch(role -> role.equals("ROLE_ADMIN"));

        location.setStatus(isAdmin ?  LocationStatus.ACCEPT : LocationStatus.PENDING);


        if(locationRequest.getImageId() != null){
            LocationImageEntity locationImageEntity = locationImageRepository.findById(locationRequest.getImageId())
                    .orElseThrow(() -> new RuntimeException("Image of location not exists"));
            location.setThumbnail(locationImageEntity);
        }

        if(locationRequest.getCollectionIds() != null){
            List<LocationImageEntity> imageCollections = locationRequest.getCollectionIds()
                    .stream()
                    .map(id -> locationImageRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Image of location not exists")))
                    .toList();
            location.setCollections(new HashSet<>(imageCollections));
        }

        return mappingOne(locationRepository.save(location));
    }
    private LocationResponse mappingOne(LocationEntity locationEntity){
        if(locationEntity  == null) return null;
        LocationResponse locationResponse = locationMapper.toLocationResponse(locationEntity);
        if(locationEntity.getThumbnail() != null){
            locationResponse.setThumbnail(locationImageMapper.toLocationImageResponse(locationEntity.getThumbnail()));
        }
        if(locationEntity.getCollections() != null) {
            locationResponse.setCollections(locationEntity.getCollections().stream().map(locationImageMapper::toLocationImageResponse).collect(Collectors.toSet()));
        }
        return locationResponse;
    }

    @Override
    public LocationResponse findByCoordinates(String lon, String lat) {
        return com.travel.travel.utils.ConvertUtils.convert(locationClient.getByCoordinates(lon,lat),LocationResponse.class);
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
                .orElseThrow(() -> new RuntimeException("Image not exists"));
        mediaClient.deleteById(locationImageEntity.getId());
        locationImageRepository.deleteById(id);
    }

    @Override
    public List<LocationResponse> findBySearchLocal(String search, Pageable pageable) {
        search = StringUtils.buildParamSearch(search);
        return mappingList(locationRepository.findBySearchParam(pageable,search, LocationStatus.ACCEPT));
    }

    @Override
    public LocationResponse findByCoordinatesLocal(String lon, String lat) {
        return mappingOne(locationRepository.findByCoordinates(lon,lat));
    }



    @Override
    public LocationResponse update(Long placeId, LocationUpdateRequest locationUpdateRequest) {

        LocationEntity location = locationRepository.findById(placeId)
                .orElseThrow(() -> new RuntimeException("Location not found"));
        LocationImageEntity thumbnail = location.getThumbnail();
        List<LocationImageEntity> images= location.getCollections() != null ? new ArrayList<>(location.getCollections()) : null;
        location = locationMapper.toLocationEntity(locationUpdateRequest);
        location.setPlace_id(placeId);
        List<LocationImageEntity> deleteImages = new ArrayList<>();
        if(locationUpdateRequest.getImageId() != null){
            if(thumbnail == null){
                LocationImageEntity locationImageEntity = locationImageRepository.findById(locationUpdateRequest.getImageId())
                        .orElseThrow(() -> new RuntimeException("Image of location not exists"));
                location.setThumbnail(locationImageEntity);
            }
            else{
               if (!thumbnail.getId().equals(locationUpdateRequest.getImageId())){
                   LocationImageEntity locationImageEntity = locationImageRepository.findById(locationUpdateRequest.getImageId())
                           .orElseThrow(() -> new RuntimeException("Image of location not exists"));
                   locationImageRepository.deleteById(location.getThumbnail().getId());
                   location.setThumbnail(locationImageEntity);
               }
            }
        }


        if(locationUpdateRequest.getCollectionIds() != null){
            if(images == null){

                List<LocationImageEntity> imageCollections = locationUpdateRequest.getCollectionIds()
                        .stream()
                        .map(id -> locationImageRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Image of location not exists")))
                        .toList();
                location.setCollections(new HashSet<>(imageCollections));
            }
            else{
                List<LocationImageEntity> imageCollections = locationUpdateRequest.getCollectionIds()
                        .stream()
                        .map(id -> locationImageRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Image of location not exists")))
                        .toList();
                Map<Long,Long> table = locationUpdateRequest.getCollectionIds()
                        .stream()
                        .collect(Collectors.toMap(
                                item -> item,
                                item -> item
                        ));

                deleteImages = images.stream()
                        .filter(image -> !table.containsKey(image.getId()))
                        .toList();
                System.out.println(deleteImages.size());

                location.setCollections(new HashSet<>(imageCollections));

            }
        }
        locationRepository.save(location);
        deleteImages.forEach(item -> mediaClient.deleteById(item.getId()));
        locationImageRepository.deleteAll(deleteImages);
        return mappingOne(location);
    }

    @Override
    public LocationResponse findById(Long id) {
        return mappingOne(locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found")));
    }

    private List<LocationResponse> mappingList(List<LocationEntity> e){
        return e.stream()
                .map(this::mappingOne)
                .toList();
    }
}
