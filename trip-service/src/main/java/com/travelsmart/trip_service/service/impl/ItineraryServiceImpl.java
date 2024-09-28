package com.travelsmart.trip_service.service.impl;

import com.travelsmart.trip_service.dto.request.*;
import com.travelsmart.trip_service.dto.response.DestinationResponse;
import com.travelsmart.trip_service.dto.response.ItineraryResponse;
import com.travelsmart.trip_service.entity.DestinationEntity;
import com.travelsmart.trip_service.entity.ItineraryEntity;
import com.travelsmart.trip_service.entity.TripEntity;
import com.travelsmart.trip_service.mapper.ItineraryMapper;
import com.travelsmart.trip_service.repository.DestinationRepository;
import com.travelsmart.trip_service.repository.ItineraryRepository;
import com.travelsmart.trip_service.repository.TripRepository;
import com.travelsmart.trip_service.repository.httpclient.LocationClient;
import com.travelsmart.trip_service.service.ItineraryService;
import com.travelsmart.trip_service.utils.DateUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItineraryServiceImpl implements ItineraryService {
    private final ItineraryRepository itineraryRepository;
    private final TripRepository tripRepository;
    private final ItineraryMapper itineraryMapper;
    private final DestinationRepository destinationRepository;
    private final LocationClient locationClient;

    @Override
    public void creatItinerariesByTrip(TripEntity trip) {

        Calendar day = Calendar.getInstance();
        day.setTime(trip.getStartDate());
        while (day.getTime().before(trip.getEndDate())){
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
               .orElseThrow(() -> new RuntimeException("Trip not found"));
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
                .orElseThrow(() -> new RuntimeException("Itinerary not found"));
        List<DestinationEntity> destinationEntities = destinationRepository.findByItineraryId(id);

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
               .orElseThrow(() -> new RuntimeException("Destination not found"));
       DestinationEntity toDestination = itinerarySwapRequest.getToId() == null ? null : destinationRepository.findById(itinerarySwapRequest.getToId())
               .orElse(null);
       if(itinerarySwapRequest.getSourceId().equals(itinerarySwapRequest.getDestinationId())){
           if(toDestination == null) throw  new RuntimeException("Destination not valid");
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
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        ItineraryEntity itinerary = itineraryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itinerary not found"));
        Calendar calendar = Calendar.getInstance();
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
                .orElseThrow(() -> new RuntimeException("Destination not found") );
        destinationRepository.updatePositionDown(destinationDeleteRequest.getItineraryId(),destination.getPosition());
        destinationRepository.deleteById(id);
    }

    private ItineraryResponse mappingOne(ItineraryEntity itineraryEntity){
        ItineraryResponse itineraryResponse = ItineraryResponse.builder()
                .id(itineraryEntity.getId())
                .note(itineraryEntity.getNote())
                .day(itineraryEntity.getDay())
                .build();
        List<DestinationResponse> destinations = destinationRepository.findByItineraryId(itineraryEntity.getId())
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
