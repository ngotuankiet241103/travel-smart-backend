package com.travelsmart.trip_service.service.impl;

import com.travelsmart.location_service.dto.response.LocationImageResponse;
import com.travelsmart.trip_service.constant.LocationType;
import com.travelsmart.trip_service.constant.TripPermission;
import com.travelsmart.trip_service.dto.request.*;
import com.travelsmart.trip_service.dto.response.*;
import com.travelsmart.trip_service.dto.response.httpclient.ProfileResponse;
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
import com.travelsmart.trip_service.repository.httpclient.ProfileClient;
import com.travelsmart.trip_service.service.ItineraryService;
import com.travelsmart.trip_service.service.TripService;
import com.travelsmart.trip_service.utils.DateUtils;
import com.travelsmart.trip_service.utils.HandleString;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {
    private final TripRepository tripRepository;
    private final TripMapper tripMapper;
    private final ItineraryService itineraryService;
    private final LocationClient locationClient;
    private final UserTripRepository userTripRepository;
    private final IdentityClient identityClient;
    private final ProfileClient profileClient;

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
        UserTripEntity userTrip= userTripRepository.findByUserIdAndTripId(userId,id)
                .orElse(null);
        if(userTrip != null) {
            userTrip.setPermission(tripShareRequest.getTripPermission());
            userTripRepository.save(userTrip);
            return "Share email " + tripShareRequest.getEmail() + " success";
        }
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
        LocationImageResponse locationImageResponse = locationClient.getLocationById(tripGenerateRequest.getLocationId()).getResult().getThumbnail();
        trip.setImage(locationImageResponse != null ? locationImageResponse.getUrl() : "");
        tripRepository.save(trip);
        buildUserTrip(trip);
        Set<LocationType> locationTypes = new HashSet<>();
        tripGenerateRequest.getType().forEach(data -> {
            locationTypes.addAll(data.getTypes());

        });
        List<ItineraryResponse> itineraries  = itineraryService.generateTrip(trip, new ArrayList<>(locationTypes));

        return TripGenerateResponse.builder()
                .id(trip.getId())
                .itineraries(itineraries)
                .title(tripGenerateRequest.getTitle())
                .startDate(tripGenerateRequest.getStartDate())
                .endDate(tripGenerateRequest.getEndDate())
                .permission(TripPermission.OWNER)
                .build();
    }

    @Override
    public List<UserTripResponse> getAllUserInTrip(Long id) {
        List<UserTripEntity> userTripEntities = userTripRepository.findByTripId(id);

        return userTripEntities.stream()
                .map(userTripEntity -> {
                    ProfileResponse userTripResponse =  profileClient.getUserById(userTripEntity.getUserId()).getResult();
                    return UserTripResponse.builder()
                            .email(userTripResponse.getEmail())
                            .tripPermission(userTripEntity.getPermission())
                            .name(userTripResponse.getUserName())
                            .avatar(userTripResponse.getAvatar())
                            .build();
                }).toList();
    }
    @Transactional
    @Override
    public String deleteById(Long id) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserTripEntity userTripEntity = userTripRepository.findByUserIdAndTripId(userName,id)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.TRIP_NOT_FOUND));
        if(!userTripEntity.getPermission().equals(TripPermission.OWNER)){
            throw new CustomRuntimeException(ErrorCode.TRIP_DENIED);
        }
        itineraryService.deleteByTripId(id);
        userTripRepository.deleteByTripId(id);
        tripRepository.deleteById(id);
        return "Delete trip success";
    }
    private Integer getWeekOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }
    @Override
    public TripReport getReport(ReportType type,Integer year,Integer month) {
        TripReport tripReport = TripReport.builder()
                .report(new HashMap<>())
                .build();

        if(type == ReportType.YEAR && year != null){
            int months = 12;
            for(int i = 1; i <= months; i++){
                long totalLocationCreated = tripRepository.countByMonthInYear(year,i);
                tripReport.getReport().put(i+"", totalLocationCreated);
            }

        }
        else if(year != null && month != null){

            List<TripEntity> locations = tripRepository.findByYearAndMonth(year, month);
            Map<String,Long> response = locations.stream()
                    .collect(Collectors.groupingBy(
                            loc -> getWeekOfMonth(loc.getCreatedDate()).toString(),
                            Collectors.counting()
                    ));
            tripReport.setReport(response);
        }
//        LocalDate today = LocalDate.now();
//        LocalDate sevenDaysAgo = today.minusDays(7);
//        while (today.isEqual(sevenDaysAgo) || today.isAfter(sevenDaysAgo)){
//            LocalDateTime startOfDay = DateUtils.setStartTimeDay(sevenDaysAgo);
//            LocalDateTime endOfDay = DateUtils.setEndTimeDay(sevenDaysAgo);
//            long totalTripCreated = tripRepository.
//                    countByCreatedDate(DateUtils.convertDateTimeToDate(startOfDay),
//                            DateUtils.convertDateTimeToDate(endOfDay));
//            tripReport.getReport().put(sevenDaysAgo.toString(), totalTripCreated);
//            sevenDaysAgo = sevenDaysAgo.plusDays(1);
//
//        }
        return tripReport;
    }

    @Override
    public Map<String, Object> getStatistics() {
        Map<String,Object> response = new HashMap<>();
        long total = tripRepository.count();
        response.put("total",total);
        response.put("label", "trip");
        return response;
    }

    @Override
    public PageableResponse<List<TripResponse>> getAll(Date from, Date to, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1,limit);
        PageableResponse<List<TripResponse>> response = null;
        if(from == null & to == null){
            Page<TripEntity> pages = tripRepository.findAll(pageable);
            response = PageableResponse.<List<TripResponse>>builder()
                    .data(mappingList(pages.getContent()))
                    .paging(Paging.buildPaging(pageable,pages.getTotalPages()))
                    .build();
        }

        return response;
    }

    private TripResponse mappingOne(TripEntity trip){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        TripResponse tripResponse = tripMapper.toTripResponse(trip);
        UserTripEntity userTripEntity = userTripRepository.findByUserIdAndTripId(authentication.getName(),trip.getId())
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.TRIP_DENIED));
        tripResponse.setPermission(userTripEntity.getPermission());
        tripResponse.setLocation(locationClient.getLocationById(trip.getLocationId()).getResult());
        return tripResponse;
    }
    private List<TripResponse> mappingList(List<TripEntity> trips){
        return trips.stream()
                .map(this::mappingOne)
                .toList();
    }
}
