package com.travelsmart.trip_service.service.impl;

import com.travelsmart.location_service.dto.response.LocationImageResponse;
import com.travelsmart.trip_service.constant.TripPermission;
import com.travelsmart.trip_service.dto.request.TripGenerateRequest;
import com.travelsmart.trip_service.dto.request.TripRequest;
import com.travelsmart.trip_service.dto.request.TripShareRequest;
import com.travelsmart.trip_service.dto.request.TripUpdateRequest;
import com.travelsmart.trip_service.dto.response.ItineraryResponse;
import com.travelsmart.trip_service.dto.response.LocationResponse;
import com.travelsmart.trip_service.dto.response.TripGenerateResponse;
import com.travelsmart.trip_service.dto.response.TripResponse;
import com.travelsmart.trip_service.entity.TripEntity;
import com.travelsmart.trip_service.entity.UserTripEntity;
import com.travelsmart.trip_service.exception.CustomRuntimeException;
import com.travelsmart.trip_service.exception.ErrorCode;
import com.travelsmart.trip_service.mapper.TripMapper;
import com.travelsmart.trip_service.repository.TripRepository;
import com.travelsmart.trip_service.repository.UserTripRepository;
import com.travelsmart.trip_service.repository.httpclient.GeoapifyClient;
import com.travelsmart.trip_service.repository.httpclient.IdentityClient;
import com.travelsmart.trip_service.repository.httpclient.LocationClient;
import com.travelsmart.trip_service.service.ItineraryService;
import com.travelsmart.trip_service.service.TripService;
import com.travelsmart.trip_service.utils.DateUtils;
import com.travelsmart.trip_service.utils.HandleString;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {
    private final TripRepository tripRepository;
    private final TripMapper tripMapper;
    private final ItineraryService itineraryService;
    private final LocationClient locationClient;
    private final UserTripRepository userTripRepository;
    private final IdentityClient identityClient;

    @Override
    public TripResponse create(TripRequest tripRequest) {

        TripEntity trip = tripMapper.toTripEntity(tripRequest);

        trip.setCode(HandleString.strToCode(trip.getTitle()));
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(tripRequest.getStartDate());
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(tripRequest.getEndDate());
        trip.setStartDate(DateUtils.setTime(startDate,23,59,59).getTime());
        trip.setEndDate(DateUtils.setTime(endDate,23,59,59).getTime());
        LocationImageResponse locationImageResponse = locationClient.getLocationById(tripRequest.getLocationId()).getResult().getThumbnail();
        trip.setImage(locationImageResponse != null ? locationImageResponse.getUrl() : "");
        tripRepository.save(trip);
        buildUserTrip(trip);
        itineraryService.creatItinerariesByTrip(trip);
        return mappingOne(trip);
    }

    private void buildUserTrip(TripEntity trip){
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        UserTripEntity userTripEntity = UserTripEntity.builder()
                .permission(TripPermission.OWNER)
                .userId(authentication.getName())
                .trip(trip)
                .build();
        userTripRepository.save(userTripEntity);
    }
    @Override
    public List<TripResponse> findMyTrip(String name) {
        return mappingList(tripRepository.findMyTrip(name));
    }

    @Override
    public TripResponse update(Long id, TripUpdateRequest tripUpdateRequest) {

        TripEntity trip = tripRepository.findById(id)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.TRIP_NOT_FOUND));
        trip.setTitle(tripUpdateRequest.getTitle());
        trip.setCode(HandleString.strToCode(trip.getTitle()));
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(tripUpdateRequest.getStartDate());
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(tripUpdateRequest.getEndDate());
        trip.setStartDate(DateUtils.setTime(startDate,23,59,59).getTime());
        trip.setEndDate(DateUtils.setTime(endDate,23,59,59).getTime());
        trip.setId(id);
        tripRepository.save(trip);
        itineraryService.updateItineraries(trip);
        return mappingOne(trip);
    }

    @Override
    public String shareTrip(Long id, TripShareRequest tripShareRequest) {
        TripEntity trip = tripRepository.findById(id)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.TRIP_NOT_FOUND));
        String userId = identityClient.getUserToShare(tripShareRequest.getEmail()).getResult();
        UserTripEntity userTripEntity = UserTripEntity.builder()
                .permission(tripShareRequest.getTripPermission())
                .userId(userId)
                .trip(trip)
                .build();
        userTripRepository.save(userTripEntity);
        return "Share email " + tripShareRequest.getEmail() + " success";
    }

    @Override
    public TripGenerateResponse generateTrip(TripGenerateRequest tripGenerateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        TripEntity trip = TripEntity.builder()
                .title(tripGenerateRequest.getTitle())
                .code(HandleString.strToCode(tripGenerateRequest.getTitle()))
                .locationId(tripGenerateRequest.getLocationId())
                .build();
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(tripGenerateRequest.getStartDate());
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(tripGenerateRequest.getEndDate());
        trip.setStartDate(DateUtils.setTime(startDate,23,59,59).getTime());
        trip.setEndDate(DateUtils.setTime(endDate,23,59,59).getTime());
//        tripRepository.save(trip);
//        buildUserTrip(trip);
        List<ItineraryResponse> itineraries  = itineraryService.generateTrip(trip,tripGenerateRequest.getType());

        return TripGenerateResponse.builder()
                .itineraries(itineraries)
                .title(tripGenerateRequest.getTitle())
                .startDate(tripGenerateRequest.getStartDate())
                .endDate(tripGenerateRequest.getEndDate())
                .build();
    }

    private TripResponse mappingOne(TripEntity trip){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        TripResponse tripResponse = tripMapper.toTripResponse(trip);
        UserTripEntity userTripEntity = userTripRepository.findByUserIdAndTripId(authentication.getName(),trip.getId())
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.TRIP_DENIED));
        tripResponse.setPermission(userTripEntity.getPermission());
        return tripResponse;
    }
    private List<TripResponse> mappingList(List<TripEntity> trips){
        return trips.stream()
                .map(this::mappingOne)
                .toList();
    }
}
