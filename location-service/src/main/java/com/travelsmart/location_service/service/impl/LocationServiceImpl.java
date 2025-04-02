package com.travelsmart.location_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelsmart.location_service.constant.LocationStatus;
import com.travelsmart.location_service.constant.LocationType;
import com.travelsmart.location_service.dto.request.*;
import com.travelsmart.location_service.dto.request.ReportType;
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
import com.travelsmart.location_service.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.io.ParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
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
    private static final double EARTH_RADIUS = 111.32;
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
        location.setDisplay_name(locationRequest.getDisplayName());
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
        search = StringUtils.buildParamSearch(search.trim());
        if( type == LocationType.ADMINISTRATIVE){
            System.out.println("type");
            return mappingList(locationRepository.findByTypeAndStatusAndAddressStateLike(LocationType.ADMINISTRATIVE,LocationStatus.ACCEPT,search));
        }

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
        LocationStatus status =  location.getStatus();
        LocationImageEntity thumbnail = location.getThumbnail();

        location = locationMapper.toLocationEntity(locationUpdateRequest);
        location.setPlace_id(placeId);
        location.setStatus(status);
        System.out.println(locationUpdateRequest.getType());
        location.setType(locationUpdateRequest.getType());
        location.setThumbnail(thumbnail);
        location.setDisplay_name(locationUpdateRequest.getDisplayName());

        if(locationUpdateRequest.getImageId() != null){
            if(thumbnail == null){
                LocationImageEntity locationImageEntity = locationImageRepository.findById(locationUpdateRequest.getImageId())
                        .orElseThrow(() -> new CustomRuntimeException(ErrorCode.IMAGE_NOT_FOUND));
                location.setThumbnail(locationImageEntity);
                locationRepository.save(location);
            }
            else{
               if (!thumbnail.getId().equals(locationUpdateRequest.getImageId())){
                   LocationImageEntity locationImageEntity = locationImageRepository.findById(locationUpdateRequest.getImageId())
                           .orElseThrow(() -> new CustomRuntimeException(ErrorCode.IMAGE_NOT_FOUND));
                    location.setThumbnail(locationImageEntity);
                   locationRepository.save(location);

                   locationImageRepository.deleteById(thumbnail.getId());

               }
               else{
                   locationRepository.save(location);
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

    @Override
    public List<LocationResponse> findByRadius(String lon, String lat, double radius, String search, LocationType type) {
        double latitude = Double.parseDouble(lat); // Điền giá trị vĩ độ tại điểm bạn muốn
        double longitude = Double.parseDouble(lon);
        double deltaLat = radius / EARTH_RADIUS;
        double deltaLon = radius / (EARTH_RADIUS * Math.cos(Math.toRadians(latitude)));
        System.out.println(search);
        // Tính tọa độ của bounding box
        double latMin = latitude - deltaLat;
        double latMax = latitude + deltaLat;
        double lonMin = longitude - deltaLon;
        double lonMax = longitude + deltaLon;
        search = StringUtils.buildParamSearch(search);
        System.out.println(search);
        if(type.equals(LocationType.ADMINISTRATIVE)){
            return mappingList(locationRepository.findByRadius(String.valueOf(latMin),String.valueOf(latMax),
                    String.valueOf(lonMin),String.valueOf(lonMax),search,LocationType.ADMINISTRATIVE));
        }
        else{
            return mappingList(locationRepository.findByRadiusAndSearchAndType(String.valueOf(latMin),String.valueOf(latMax),
                    String.valueOf(lonMin),String.valueOf(lonMax),search,type));
        }


    }
    private Integer getWeekOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }


    @Override
    public LocationReport getReport(ReportType type,Integer year,Integer month) {
        LocationReport locationReport = LocationReport.builder()
                .report(new HashMap<>())
                .build();
        if(type == ReportType.YEAR && year != null){
            int months = 12;
            for(int i = 1; i <= months; i++){
                long totalLocationCreated = locationRepository.countByMonthInYear(year,i);
                locationReport.getReport().put(i+"", totalLocationCreated);
            }

        }
        else if(year != null && month != null){

            List<LocationEntity> locations = locationRepository.findByYearAndMonth(year, month);
            Map<String,Long> response = locations.stream()
                    .collect(Collectors.groupingBy(
                            loc -> getWeekOfMonth(loc.getCreatedDate()).toString(),
                            Collectors.counting()
                    ));
            locationReport.setReport(response);
        }


//        LocalDate today = LocalDate.now();
//        LocalDate sevenDaysAgo = today.minusDays(7);
//        while (today.isEqual(sevenDaysAgo) || today.isAfter(sevenDaysAgo)){
//            LocalDateTime startOfDay = DateUtils.setStartTimeDay(sevenDaysAgo);
//            LocalDateTime endOfDay = DateUtils.setEndTimeDay(sevenDaysAgo);
//            long totalTripCreated = locationRepository.
//                    countByCreatedDate(DateUtils.convertDateTimeToDate(startOfDay),
//                            DateUtils.convertDateTimeToDate(endOfDay));
//            locationReport.getReport().put(sevenDaysAgo.toString(), totalTripCreated);
//            sevenDaysAgo = sevenDaysAgo.plusDays(1);
//
//        }
        return locationReport;
    }

    @Override
    public Map<String, Object> getStatistics() {
        Map<String,Object> response = new HashMap<>();
        long total = locationRepository.countByStatus(LocationStatus.ACCEPT);
        response.put("total",total);
        response.put("label","location");
        return response;
    }

    private List<LocationResponse> mappingList(List<LocationEntity> e){
        System.out.println(e.size());
        return e.stream()
                .map(this::mappingOne)
                .toList();
    }
}
