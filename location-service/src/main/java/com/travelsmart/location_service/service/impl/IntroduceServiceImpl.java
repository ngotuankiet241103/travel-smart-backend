package com.travelsmart.location_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelsmart.event.dto.CheckInRequest;
import com.travelsmart.location_service.dto.request.IntroduceRequest;
import com.travelsmart.location_service.dto.request.IntroduceUpdateRequest;
import com.travelsmart.location_service.dto.response.IntroduceResponse;
import com.travelsmart.location_service.entity.IntroduceLocationEntity;
import com.travelsmart.location_service.entity.LocationEntity;
import com.travelsmart.location_service.entity.LocationImageEntity;
import com.travelsmart.location_service.exception.CustomRuntimeException;
import com.travelsmart.location_service.exception.ErrorCode;
import com.travelsmart.location_service.mapper.IntroduceMapper;
import com.travelsmart.location_service.mapper.LocationImageMapper;
import com.travelsmart.location_service.repository.IntroduceLocationRepository;
import com.travelsmart.location_service.repository.LocationImageRepository;
import com.travelsmart.location_service.repository.LocationRepository;
import com.travelsmart.location_service.repository.httpclient.MediaClient;
import com.travelsmart.location_service.service.IntroduceService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.scheduling.annotation.Async;
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
public class IntroduceServiceImpl implements IntroduceService {
    private final IntroduceLocationRepository repository;
    private final IntroduceMapper introduceMapper;
    private final LocationImageRepository locationImageRepository;
    private final LocationImageMapper locationImageMapper;
    private final MediaClient mediaClient;
    private final LocationRepository locationRepository;
    private final KafkaTemplate<String,Object> template;
    private final ObjectMapper objectMapper;
    @Override
    public IntroduceResponse create(IntroduceRequest introduceRequest) {
        IntroduceLocationEntity introduceLocationEntity = introduceMapper.
                toIntroduceLocationEntity(introduceRequest);
        introduceLocationEntity.setLocation(LocationEntity.builder()
                        .place_id(introduceRequest.getLocationId()).build());
        if(introduceRequest.getCollectionIds() != null){
            List<LocationImageEntity> imageCollections = introduceRequest.getCollectionIds()
                    .stream()
                    .map(id -> locationImageRepository.findById(id)
                            .orElseThrow(() -> new CustomRuntimeException(ErrorCode.IMAGE_NOT_FOUND)))
                    .toList();
            introduceLocationEntity.setCollections(new HashSet<>(imageCollections));
        }
        return mappingOne(repository.save(introduceLocationEntity));
    }

    @Override
    public IntroduceResponse getByLocationId(Long locationId) {
        cacheLocation(locationId);
        return mappingOne(repository.findByLocationId(locationId)
                .orElse(null));
    }
    @Async
    private void cacheLocation(Long locationId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) return;
        String userId = authentication.getName();
        LocationEntity location = locationRepository.findById(locationId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.LOCATION_NOT_FOUND));
        CheckInRequest checkInRequest = CheckInRequest.builder()
                .userId(userId)
                .type(location.getType())
                .build();
        template.setMessageConverter(new StringJsonMessageConverter(objectMapper));
        template.send("new-checkIn", checkInRequest);
        System.out.println("send success");
    }
    @Override
    public IntroduceResponse update(Long introduceId, IntroduceUpdateRequest updateRequest) {
        IntroduceLocationEntity introduceLocation = repository.findById(introduceId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.INTRODUCE_NOT_FOUND));
        introduceLocation.setContent(updateRequest.getContent());
        introduceLocation.setTitle(updateRequest.getTitle());
        List<LocationImageEntity> images= introduceLocation.getCollections() != null ? new ArrayList<>(introduceLocation.getCollections()) : null;
        List<LocationImageEntity> deleteImages = new ArrayList<>();
        if(updateRequest.getCollectionIds() != null){
            if(images == null){

                List<LocationImageEntity> imageCollections = updateRequest.getCollectionIds()
                        .stream()
                        .map(id -> locationImageRepository.findById(id)
                                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.IMAGE_NOT_FOUND)))
                        .toList();
                introduceLocation.setCollections(new HashSet<>(imageCollections));
            }
            else{
                List<LocationImageEntity> imageCollections = updateRequest.getCollectionIds()
                        .stream()
                        .map(id -> locationImageRepository.findById(id)
                                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.IMAGE_NOT_FOUND)))
                        .toList();
                Map<Long,Long> table = updateRequest.getCollectionIds()
                        .stream()
                        .collect(Collectors.toMap(
                                item -> item,
                                item -> item
                        ));

                deleteImages = images.stream()
                        .filter(image -> !table.containsKey(image.getId()))
                        .toList();
                System.out.println(deleteImages.size());

               introduceLocation.setCollections(new HashSet<>(imageCollections));

            }
        }
        repository.save(introduceLocation);
        System.out.println(deleteImages.size());
        deleteImages.forEach(item -> mediaClient.deleteById(item.getId()));
        locationImageRepository.deleteAll(deleteImages);
        return mappingOne(introduceLocation);
    }

    private IntroduceResponse mappingOne(IntroduceLocationEntity introduceLocation){
        if(introduceLocation == null) return null;
        IntroduceResponse response = introduceMapper.toIntroduceResponse(introduceLocation);
        response.setCollections(introduceLocation.getCollections() != null ?
                new ArrayList<>(introduceLocation.getCollections()).stream()
                    .map(locationImageMapper::toLocationImageResponse)
                    .toList() :
                null);
        return response;
    }
}
