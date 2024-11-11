package com.travelsmart.trip_service.service.impl;

import com.travelsmart.trip_service.constant.LocationType;
import com.travelsmart.trip_service.dto.request.*;
import com.travelsmart.trip_service.dto.response.DestinationResponse;
import com.travelsmart.trip_service.dto.response.ItineraryResponse;
import com.travelsmart.trip_service.dto.response.LocationResponse;
import com.travelsmart.trip_service.dto.response.httpclient.DistanceResponse;
import com.travelsmart.trip_service.dto.response.httpclient.ProfileResponse;
import com.travelsmart.trip_service.entity.DestinationEntity;
import com.travelsmart.trip_service.entity.ItineraryEntity;
import com.travelsmart.trip_service.entity.TripEntity;
import com.travelsmart.trip_service.exception.CustomRuntimeException;
import com.travelsmart.trip_service.exception.ErrorCode;
import com.travelsmart.trip_service.mapper.ItineraryMapper;
import com.travelsmart.trip_service.repository.DestinationRepository;
import com.travelsmart.trip_service.repository.ItineraryRepository;
import com.travelsmart.trip_service.repository.TripRepository;
import com.travelsmart.trip_service.repository.httpclient.GeoapifyClient;
import com.travelsmart.trip_service.repository.httpclient.LocationClient;
import com.travelsmart.trip_service.repository.httpclient.ProfileClient;
import com.travelsmart.trip_service.service.ItineraryService;
import com.travelsmart.trip_service.utils.NearestNeighborTSP;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ItineraryServiceImpl implements ItineraryService {
    private final ItineraryRepository itineraryRepository;
    private final TripRepository tripRepository;
    private final ItineraryMapper itineraryMapper;
    private final DestinationRepository destinationRepository;
    private final LocationClient locationClient;
    private final GeoapifyClient geoapifyClient;
    private final ProfileClient profileClient;

    @Override
    public void creatItinerariesByTrip(TripEntity trip) {

        Calendar day = Calendar.getInstance();
        day.setTime(trip.getStartDate());
        while (day.getTime().compareTo(trip.getEndDate()) <= 0 ){
            ItineraryEntity itinerary = ItineraryEntity.builder()
                    .day(day.getTime())
                    .trip(trip)
                    .build();
            itineraryRepository.save(itinerary);
            day.add(Calendar.DAY_OF_MONTH,1);
        }
    }
    @Transactional
    @Override
    public void updateItineraries(TripEntity trip) {
        List<ItineraryEntity> itineraries = itineraryRepository.findByTripId(trip.getId());
        Map<Date,Date> table = new HashMap<>();
        Calendar day = Calendar.getInstance();
        day.setTime(trip.getStartDate());
        Map<Date,Object> items  = new ArrayList<>(itineraries).stream()
                .filter(itineraryEntity -> (itineraryEntity.getDay().compareTo(trip.getStartDate()) >= 0 && itineraryEntity.getDay().compareTo(trip.getEndDate()) <= 0))
                .collect(Collectors.toMap(ItineraryEntity::getDay, ItineraryEntity::getDay));
        List<ItineraryEntity> deleteItineraries = itineraries.stream()
                .filter(itineraryEntity -> !(itineraryEntity.getDay().compareTo(trip.getStartDate()) >= 0 && itineraryEntity.getDay().compareTo(trip.getEndDate()) <= 0))
                .toList();



        while (day.getTime().compareTo(trip.getEndDate()) <= 0){
            if(!items.containsKey(day.getTime())){
                ItineraryEntity itinerary = ItineraryEntity.builder()
                        .day(day.getTime())
                        .trip(trip)
                        .build();
                itineraryRepository.save(itinerary);
                table.put(day.getTime(),day.getTime());

            }
            day.add(Calendar.DAY_OF_MONTH,1);
        }
        deleteItineraries.forEach(itineraryEntity -> {
            destinationRepository.deleteByItineraryId(itineraryEntity.getId());
        });
        itineraryRepository.deleteArrange(trip.getId(),trip.getStartDate(),trip.getEndDate());

    }

    @Override
    public ItineraryResponse createItinerary(Long tripId, ItineraryCreateRequest itineraryCreateRequest) {
       TripEntity trip = tripRepository.findById(tripId)
               .orElseThrow(() -> new CustomRuntimeException(ErrorCode.TRIP_NOT_FOUND));
       ItineraryEntity itineraryEntity = itineraryMapper.toItineraryEntity(itineraryCreateRequest);
       Calendar calendar = Calendar.getInstance();
       calendar.setTime(trip.getEndDate());
       calendar.add(Calendar.DAY_OF_MONTH,1);
       itineraryEntity.setDay(calendar.getTime());
        itineraryEntity.setTrip(trip);
        trip.setEndDate(calendar.getTime());
        tripRepository.save(trip);
        return mappingOne(itineraryRepository.save(itineraryEntity));
    }

    @Override
    public ItineraryResponse create(Long id, ItineraryRequest itineraryRequest) {
        ItineraryEntity itinerary = itineraryRepository.findById(id)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.ITINERARY_NOT_FOUND));
        List<DestinationEntity> destinationEntities = destinationRepository.findByItineraryIdOrderByPosition(id);

        DestinationEntity destination = DestinationEntity.builder()
                .itinerary(itinerary)
                .locationId(itineraryRequest.getLocationId())
                .position(destinationEntities == null || destinationEntities.isEmpty() ? 1 : destinationEntities.get(destinationEntities.size() - 1).getPosition() + 1)
                .build();
        destinationRepository.save(destination);
        return mappingOne(itinerary);
    }

    @Override
    public List<ItineraryResponse> findByTripId(Long tripId) {
        return mappingList(itineraryRepository.findByTripId(tripId));
    }
    @Transactional
    @Override
    public ItineraryResponse swapItinerary(ItinerarySwapRequest itinerarySwapRequest) {
       DestinationEntity fromDestination = destinationRepository.findById(itinerarySwapRequest.getFromId())
               .orElseThrow(() -> new CustomRuntimeException(ErrorCode.DESTINATION_NOT_FOUND));
       DestinationEntity toDestination = itinerarySwapRequest.getToId() == null ? null : destinationRepository.findById(itinerarySwapRequest.getToId())
               .orElse(null);
       if(itinerarySwapRequest.getSourceId().equals(itinerarySwapRequest.getDestinationId())){
           if(toDestination == null) throw  new CustomRuntimeException(ErrorCode.DESTINATION_INVALID);
           if(fromDestination.getPosition() > toDestination.getPosition()){
               destinationRepository.moveUpPosition(itinerarySwapRequest.getSourceId(),toDestination.getPosition(),fromDestination.getPosition());
           }
           else {
               destinationRepository.moveDownPosition(itinerarySwapRequest.getSourceId(),fromDestination.getPosition(),toDestination.getPosition());
           }
           fromDestination.setPosition(toDestination.getPosition());
       }
       else {

           if(toDestination != null){
               destinationRepository.updatePositionUp(itinerarySwapRequest.getDestinationId(), toDestination.getPosition() );
           }
           destinationRepository.updatePositionDown(itinerarySwapRequest.getSourceId(),fromDestination.getPosition());
           fromDestination.setItinerary(ItineraryEntity.builder()
                           .id(itinerarySwapRequest.getDestinationId())
                   .build());
           fromDestination.setPosition(toDestination == null ? 1 : toDestination.getPosition());

       }
       destinationRepository.save(fromDestination);
        return null;
    }

    @Override
    public void deleteById(Long id, ItineraryDeleteRequest itineraryDeleteRequest) {
        TripEntity trip = tripRepository.findById(itineraryDeleteRequest.getTripId())
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.TRIP_NOT_FOUND));
        ItineraryEntity itinerary = itineraryRepository.findById(id)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.ITINERARY_NOT_FOUND));
        Calendar calendar = Calendar.getInstance();
        List<DestinationEntity> destinationEntities = destinationRepository.findByItineraryIdOrderByPosition(itinerary.getId());
        destinationRepository.deleteAll(destinationEntities);
        if(trip.getStartDate().compareTo(itinerary.getDay()) == 0){
            calendar.setTime(trip.getStartDate());
            calendar.add(Calendar.DAY_OF_MONTH,1);
            trip.setStartDate(calendar.getTime());
        }
        else{
            calendar.setTime(trip.getEndDate());
            calendar.add(Calendar.DAY_OF_MONTH,-1);
            trip.setEndDate(calendar.getTime());
        }
        if(trip.getStartDate().compareTo(trip.getEndDate()) > 0 ||
            trip.getEndDate().compareTo(trip.getStartDate()) < 0
        ) {
            trip.setStartDate(null);
            trip.setEndDate(null);
        }
        itineraryRepository.deleteById(itinerary.getId());
        tripRepository.save(trip);

    }
    @Transactional
    @Override
    public void deleteDestinationById(Long id, DestinationDeleteRequest destinationDeleteRequest) {
        DestinationEntity destination = destinationRepository.findById(id)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.DESTINATION_NOT_FOUND));
        destinationRepository.updatePositionDown(destinationDeleteRequest.getItineraryId(),destination.getPosition());
        destinationRepository.deleteById(id);
    }

    @Override
    public List<ItineraryResponse>  generateTrip(TripEntity trip, List<LocationType> types) {

        List<LocationResponse> locationResponses = locationClient.getByTypes(types,trip.getLocationId()).getResult();
        System.out.println(locationResponses.size());
        int col = 0;
        Map<String,DistanceResponse> table = new HashMap<>();
        DistanceResponse[][] distancesMatrix = new DistanceResponse[locationResponses.size()][locationResponses.size()] ;
        for(int i = 0; i <= locationResponses.size() - 1; i++){
            for(int j = 0 ; j <= locationResponses.size() - 1;j++){
                if(i== j){
                    distancesMatrix[j][col] = new DistanceResponse();
                }
                String regex1 = locationResponses.get(i).getPlace_id()+ "-" + locationResponses.get(j).getPlace_id();
                String regex2 = locationResponses.get(j).getPlace_id()+ "-" + locationResponses.get(i).getPlace_id();
                if(table.containsKey(regex1) || table.containsKey(regex2)){
                    distancesMatrix[j][col] = table.get(regex1);
                }
                else{
                    StringBuilder waypoints = new StringBuilder();
                    waypoints.append(locationResponses.get(i).getLat());
                    waypoints.append(",");
                    waypoints.append(locationResponses.get(i).getLon());
                    waypoints.append("|");
                    waypoints.append( locationResponses.get(j).getLat());
                    waypoints.append(",");
                    waypoints.append(locationResponses.get(j).getLon());

                    Map<String,Object> objectMap = geoapifyClient.routingLocation(waypoints.toString());
                    System.out.println(objectMap);
                    List<Map<String,Object>> arr = (List<Map<String, Object>>) objectMap.get("features");
                    Map<String,Object> obj = arr.get(0);
                    Map<String,Object> properties = (Map<String, Object>) obj.get("properties");
                    double time  = Double.parseDouble(properties.get("time").toString());
                    double distance = Double.parseDouble(properties.get("distance").toString());
                    System.out.println("time" +  time);
                    System.out.println("distance" +  distance);
                    DistanceResponse distanceResponse = DistanceResponse.builder()
                            .distance(distance)
                            .time(time)
                            .build();
                    table.put(regex1,distanceResponse);
                    table.put(regex2,distanceResponse);
                    distancesMatrix[j][col] = distanceResponse;
                }

            }
            col++;
        }
        double[] timeVisits = new double[locationResponses.size()];
        boolean[] visited = new boolean[distancesMatrix.length];
        for(int i = 0; i <= distancesMatrix.length - 1 ; i ++){
            for(int j = 0; j <= distancesMatrix[i].length - 1; j++){
                System.out.print(distancesMatrix[i][j].getDistance() +" ");
            }
            System.out.println("");
        }
        Calendar day = Calendar.getInstance();
        day.setTime(trip.getStartDate());
        List<ItineraryResponse> itineraryResponses = new ArrayList<>();
        visited[0] = true;
        boolean isFirst = true;
        int currentLocation = 0;

        while (day.getTime().compareTo(trip.getEndDate()) <= 0){

            Map<String,Object> response  = NearestNeighborTSP.planDay(distancesMatrix,visited,timeVisits,8 * 60,currentLocation);

            List<Integer> path = (List<Integer>) response.get("path");
            visited = (boolean[]) response.get("visited");
            currentLocation = Integer.parseInt(response.get("currentLocation").toString());
            for(int i : path){
                System.out.println(i);
            }

            ItineraryEntity itineraryEntity = ItineraryEntity.builder()
                    .trip(trip)
                    .day(day.getTime())
                    .build();
            itineraryRepository.save(itineraryEntity);
            AtomicInteger position = new AtomicInteger(1);
            List<DestinationEntity>  destinations =  path.stream()
                    .map(index -> {
                        DestinationEntity destination = DestinationEntity.builder()
                                .position(position.get())
                                .itinerary(itineraryEntity)
                                .locationId(locationResponses.get(index).getPlace_id())
                                .build();
                        position.getAndIncrement();
                        return destination;
                    }).collect(Collectors.toList());
            destinationRepository.saveAll(destinations);
            day.add(Calendar.DAY_OF_MONTH,1);
            itineraryResponses.add(mappingOne(itineraryEntity));

        }



        return itineraryResponses;
    }

    @Override
    public List<ItineraryResponse> optimzeItinerariesInTrip(Long tripId) {
        List<ItineraryEntity> itineraryEntities = itineraryRepository.findByTripId(tripId);
        itineraryEntities.forEach(itineraryEntity -> {
            List<DestinationEntity> destinationEntities = destinationRepository.findByItineraryIdOrderByPosition(itineraryEntity.getId());
            if(destinationEntities.isEmpty()) return;;
            List<LocationResponse> locationResponses = destinationEntities
                    .stream()
                    .map(DestinationEntity::getLocationId)
                    .map(locationId -> locationClient.getLocationById(locationId).getResult())
                    .toList();
            double[][] distancesMatrix = new double[locationResponses.size()][locationResponses.size()] ;
            int col = 0;
            Map<String,Double> table = new HashMap<>();
            for(int i = 0; i <= locationResponses.size() - 1; i++){
                for(int j = 0 ; j <= locationResponses.size() - 1;j++){
                    if(i== j){
                        distancesMatrix[j][col] = 0;
                    }
                    String regex1 = locationResponses.get(i).getPlace_id()+ "-" + locationResponses.get(j).getPlace_id();
                    String regex2 = locationResponses.get(j).getPlace_id()+ "-" + locationResponses.get(i).getPlace_id();
                    if(table.containsKey(regex1) || table.containsKey(regex2)){
                        distancesMatrix[j][col] = table.get(regex1);
                    }
                    else{
                        StringBuilder waypoints = new StringBuilder();
                        waypoints.append(locationResponses.get(i).getLat());
                        waypoints.append(",");
                        waypoints.append(locationResponses.get(i).getLon());
                        waypoints.append("|");
                        waypoints.append( locationResponses.get(j).getLat());
                        waypoints.append(",");
                        waypoints.append(locationResponses.get(j).getLon());
                        System.out.println("waypoints: "+ waypoints.toString());
                        Map<String,Object> objectMap = geoapifyClient.routingLocation(waypoints.toString());
                        System.out.println(objectMap);
                        List<Map<String,Object>> arr = (List<Map<String, Object>>) objectMap.get("features");
                        Map<String,Object> obj = arr.get(0);
                        Map<String,Object> properties = (Map<String, Object>) obj.get("properties");
                        double time  = Double.parseDouble(properties.get("time").toString());
                        double distance = Double.parseDouble(properties.get("distance").toString());
                        System.out.println("time" +  time);
                        System.out.println("distance" +  distance);
                        DistanceResponse distanceResponse = DistanceResponse.builder()
                                .distance(distance)
                                .time(time)
                                .build();
                        table.put(regex1,distance);
                        table.put(regex2,distance);
                        distancesMatrix[j][col] = distance;
                    }

                }
                col++;
            }
            for(int i = 0; i <= distancesMatrix.length - 1 ; i ++){
                for(int j = 0; j <= distancesMatrix[i].length - 1; j++){
                    System.out.print(distancesMatrix[i][j] +" ");
                }
                System.out.println("");
            }

            int[] path = NearestNeighborTSP.nearestNeighbor(distancesMatrix,0);
            for(int i : path) {
                System.out.print(i + " ");
            }
            List<Long> destinationIds = Arrays.stream(path)
                    .boxed()
                    .map(locationId -> {
                return destinationEntities.get(locationId).getId();
            }).toList();
            AtomicInteger position = new AtomicInteger(1);
            destinationIds.forEach(destinationId -> {
                destinationRepository.updatePositionById(destinationId,position.get());
                position.getAndIncrement();
            });
        });



        return null;
    }

    @Override
    public void deleteByTripId(Long id) {
        List<ItineraryEntity> itineraries = itineraryRepository.findByTripId(id);
        itineraries.stream().forEach(itineraryEntity ->  {
            List<DestinationEntity> destinations = destinationRepository.findByItineraryIdOrderByPosition(itineraryEntity.getId());
            destinationRepository.deleteAll(destinations);
            itineraryRepository.delete(itineraryEntity);
        });

    }

    @Override
    public ItineraryResponse updateItinerary(Long id, ItineraryNoteUpdateRequest updateRequest) {
        ItineraryEntity itineraryEntity = itineraryRepository.findById(id)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.ITINERARY_NOT_FOUND));
        itineraryEntity.setNote(updateRequest.getNote());
        return mappingOne(itineraryRepository.save(itineraryEntity));
    }

    private ItineraryResponse mappingOne(ItineraryEntity itineraryEntity){
        ItineraryResponse itineraryResponse = ItineraryResponse.builder()
                .id(itineraryEntity.getId())
                .note(itineraryEntity.getNote())
                .day(itineraryEntity.getDay())
                .build();
        List<DestinationResponse> destinations = destinationRepository.findByItineraryIdOrderByPosition(itineraryEntity.getId())
                .stream()
                .map(destinationEntity -> {
                    return DestinationResponse.builder()
                            .position(destinationEntity.getPosition())
                            .location(locationClient.getLocationById(destinationEntity.getLocationId()).getResult())
                            .id(destinationEntity.getId())
                            .build();
                })
                .toList();
        itineraryResponse.setDestinations(destinations);
        return  itineraryResponse;
    }
    private List<ItineraryResponse> mappingList(List<ItineraryEntity> e){
        return e.stream()
                .map(this::mappingOne)
                .toList();
    }
}
